package com.zju.chmwang;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SmartMeter {
	private HashMap<Integer, Double> powerDatabaseTable = new HashMap<Integer, Double>();
	private ApplicationContext context = 
			new ClassPathXmlApplicationContext("Beans.xml");
	private EventJDBCTemplate eventJDBCTemplate = 
			(EventJDBCTemplate)context.getBean("eventJDBCTemplate");
	
	public SmartMeter() {
		BuildPowerDatabase appliancePowerDatabase = new BuildPowerDatabase();
		powerDatabaseTable = appliancePowerDatabase.getPowerDatabase();
	}
	
	private Boolean isTriggerStable(Double stableDelta, Double triggerStart, LinkedList<Double> triggerStablePowerList) {
		if (triggerStablePowerList.size() < 2) return true;
		
		Double deltaPowerSum = 0.0;
		for (int i = 0; i < triggerStablePowerList.size()-1; ++i) {
			deltaPowerSum += Parameter.abs(triggerStablePowerList.get(i+1)-triggerStablePowerList.get(i));
			if (deltaPowerSum >= stableDelta) return false;
		}
		return true;
	}
	
	private Boolean triggerDetectionCheck(Double triggerStart, Double triggerEnd, Double triggerDelta) {
		if (Parameter.abs(triggerEnd-triggerStart) < triggerDelta) return false;
		return true;
	}
	
	private String listToString(LinkedList<Double> triggerProcedurePowerList) {
		String triggerProcedurePowerListString = "";
		DecimalFormat df = new DecimalFormat("#.00");
		for (Double power : triggerProcedurePowerList) {
			triggerProcedurePowerListString += (df.format(power)+",");
		}
		return triggerProcedurePowerListString.substring(0, triggerProcedurePowerListString.length()-1);
	}
	
	private LinkedList<Double> stringToList(String triggerProcedurePowerListString) {
		LinkedList<Double> triggerProcedurePowerList = new LinkedList<Double>();
		String[] power = triggerProcedurePowerListString.split(",");
		for (int i = 0; i < power.length; ++i)
			triggerProcedurePowerList.add(Double.parseDouble(power[i]));
		return triggerProcedurePowerList;
	}
	
	private LinkedList<Double> triggerProcedurePowerListPreTreatment(LinkedList<Double> triggerProcedurePowerList) {
		if (triggerProcedurePowerList.size() < 2) return null;
		
		LinkedList<Double> triggerProcedureDeltaPowerList = new LinkedList<Double>();
		for (int i = 0; i < triggerProcedurePowerList.size()-1; ++i)
			triggerProcedureDeltaPowerList.add(triggerProcedurePowerList.get(i+1) - 
					triggerProcedurePowerList.get(i));
		return triggerProcedureDeltaPowerList;
	}
	
	private Double triggerProcedureDeltaPowerListSimilarity(LinkedList<Double> shortTriggerProcedureDeltaPowerList,
			LinkedList<Double> longTriggerProcedureDeltaPowerList) {
		Double minDiffSum = 1.0*Parameter.INF;
		for (int i = 0; i < longTriggerProcedureDeltaPowerList.size()-
				shortTriggerProcedureDeltaPowerList.size()+1; ++i) {
			Double diffSum = 0.0;
			for (int j = 0; j < shortTriggerProcedureDeltaPowerList.size(); ++j)
				diffSum += (shortTriggerProcedureDeltaPowerList.get(j)-
						longTriggerProcedureDeltaPowerList.get(i+j));
			if (diffSum < minDiffSum) minDiffSum = diffSum;
		}
		return minDiffSum;
	}
	
	private Double triggerProcedurePowerListSimilarity(LinkedList<Double> triggerProcedurePowerList,
			LinkedList<Double> recordTriggerProcedurePowerList) {
		LinkedList<Double> triggerProcedureDeltaPowerList = 
				triggerProcedurePowerListPreTreatment(triggerProcedurePowerList);
		LinkedList<Double> recordTriggerProcedureDeltaPowerList = 
				triggerProcedurePowerListPreTreatment(recordTriggerProcedurePowerList);
		if (triggerProcedureDeltaPowerList.size() < recordTriggerProcedureDeltaPowerList.size())
			return triggerProcedureDeltaPowerListSimilarity(triggerProcedureDeltaPowerList, 
					recordTriggerProcedureDeltaPowerList);
		return triggerProcedureDeltaPowerListSimilarity(recordTriggerProcedureDeltaPowerList, 
				triggerProcedureDeltaPowerList);
	}
	
	private Integer findApplianceID(Double triggerStart, Double triggerEnd, 
			Stack<Appliance> stAppliance) {
		Double appliancePower = Parameter.abs(triggerEnd - triggerStart);
		Double diffPower = -1.0, minDiffPower = 1.0*Parameter.INF;
		Integer applianceID = -1;
		for (Appliance app : stAppliance) {
			diffPower = Parameter.abs(app.getPower() - appliancePower);
			if (diffPower < minDiffPower) {
				minDiffPower = diffPower;
				applianceID = app.getApplianceID();
			}
		}
		return applianceID;
	}
	
	private Integer findApplianceIDByDetailPower(LinkedList<Double> triggerProcedurePowerList, 
			List<Event> events) {
		Integer applianceID = -1;
		for (Event record : events) {
			LinkedList<Double> recordTriggerPowerProcedureList = stringToList(
					record.getTriggerProcedurePowerList());
			Double similarity = triggerProcedurePowerListSimilarity(
					triggerProcedurePowerList, recordTriggerPowerProcedureList);
			Double minSimilarity = 1.0 * Parameter.INF;
			if (similarity < minSimilarity) {
				minSimilarity = similarity;
				applianceID = record.getApplianceID();
			}
		}
		return applianceID;
	}
	
	private Integer findApplianceIDByPower(Double triggerStart, Double triggerEnd, 
			LinkedList<Integer> candidateAppIDList) {
		Double appliancePower = Parameter.abs(triggerEnd - triggerStart);
		Double diffPower = -1.0, minDiffPower = 1.0*Parameter.INF;
		Integer applianceID = -1;
		for (Integer candidateAppID : candidateAppIDList) {
			diffPower = Parameter.abs(powerDatabaseTable.get(candidateAppID)-appliancePower);
			if (diffPower < minDiffPower) {
				minDiffPower = diffPower;
				applianceID = candidateAppID;
			}
		}
		return applianceID;
	}
	
	private LinkedList<Integer> findApplianceIDListByDuration(LinkedList<Double> triggerProcedurePowerList, 
			LinkedList<Integer> candidateAppIDListByOverallPower, List<Event> events) {
		Integer duration = triggerProcedurePowerList.size();
		Double fluctuation = duration * Parameter.tolerateFluctuationRate;
		Integer fluctuationInt = fluctuation.intValue();
		LinkedList<Integer> candidateAppIDList = new LinkedList<Integer>();
		Integer diffDuration = -1, minDiffDuration = Parameter.INF;
		Integer applianceID = -1;
		for (Integer candidateAppID : candidateAppIDListByOverallPower) {
			for (Event record : events) {
				if (record.getApplianceID() == candidateAppID) {
					diffDuration = Parameter.abs(record.getTriggerProcedurePowerNumbers()-duration);
					if (diffDuration<fluctuationInt && !candidateAppIDList.contains(candidateAppID)) 
						candidateAppIDList.add(candidateAppID);
					if (diffDuration < minDiffDuration) {
						minDiffDuration = diffDuration; applianceID = candidateAppID;
					}
				}
			}
		}
		if (applianceID == -1) {
			applianceID = findApplianceIDByPower(triggerProcedurePowerList.getFirst(), 
					triggerProcedurePowerList.getLast(), candidateAppIDListByOverallPower);
			candidateAppIDList.add(applianceID);
		}
		else if (candidateAppIDList.isEmpty()) candidateAppIDList.add(applianceID);
		return candidateAppIDList;
	}
	
	private LinkedList<Integer> findCandidateApplianceIDListByOverallPower(
			Double triggerStart, Double triggerEnd) {
		Double appliancePower = Parameter.abs(triggerEnd - triggerStart);
		Double fluctuation = appliancePower * Parameter.tolerateFluctuationRate;
		LinkedList<Integer> candidateAppIDList = new LinkedList<Integer>();
		Double diffPower = -1.0, minDiffPower = 1.0*Parameter.INF;
		Integer applianceID = -1;
		
		Iterator<Entry<Integer, Double>> iter = powerDatabaseTable.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<Integer, Double> entry = iter.next();
			Integer key = entry.getKey();
			Double value = entry.getValue();
			diffPower = Parameter.abs(appliancePower-value);
			if (diffPower < fluctuation) candidateAppIDList.add(key);
			if (diffPower < minDiffPower) {
				minDiffPower = diffPower; applianceID = key;
			}
		}
		if (candidateAppIDList.isEmpty()) candidateAppIDList.add(applianceID);
		return candidateAppIDList;
	}
	
	private Integer findApplianceID(LinkedList<Double> triggerProcedurePowerList) {
		LinkedList<Integer> candidateAppIDListByOverallPower = findCandidateApplianceIDListByOverallPower(
				triggerProcedurePowerList.getFirst(), triggerProcedurePowerList.getLast());
		if (candidateAppIDListByOverallPower.size() < 2) return candidateAppIDListByOverallPower.peek();
		
		List<Event> events = eventJDBCTemplate.listEvents();
		if (events.isEmpty())
			return findApplianceIDByPower(triggerProcedurePowerList.getFirst(), 
					triggerProcedurePowerList.getLast(), candidateAppIDListByOverallPower);
		LinkedList<Integer> candidateAppIDListByDuration = findApplianceIDListByDuration(
				triggerProcedurePowerList, candidateAppIDListByOverallPower, events);
		if (candidateAppIDListByDuration.size() < 2) return candidateAppIDListByDuration.peek();
		
		return findApplianceIDByDetailPower(triggerProcedurePowerList, events);
	}
	
	public void solve(String fileName) {
		try {
			File readFile = new File(fileName);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(readFile));
			BufferedReader bufr = new BufferedReader(reader);
			
			File detectFile = new File(Parameter.eventDetectFile);
			detectFile.createNewFile();
			BufferedWriter bufDetect = new BufferedWriter(new FileWriter(detectFile));
			
			Boolean enTrigger = false, deTrigger = false; Integer index = 0;
			Timestamp startTime=null, endTime=null;
			Double beforeLast = -1.0, lastPower = -1.0, curPower = -1.0, triggerStart = -1.0;
			Stack<Appliance> stAppliance = new Stack<Appliance>();
			LinkedList<Double> triggerStablePowerList = new LinkedList<Double>();
			LinkedList<Double> triggerProcedurePowerList = new LinkedList<Double>();
			
			for (String line = bufr.readLine(); line != null; line = bufr.readLine(), ++index) {
				String[] item = line.split(" ");
				if (index == 0) {
					beforeLast = Double.parseDouble(item[4]);
					continue;
				} else if (index == 1) {
					lastPower = Double.parseDouble(item[4]);
					continue;
				}
				
				String dateTime = item[1] + " " + item[2];
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				timestamp = Timestamp.valueOf(dateTime);
				
				curPower = Double.parseDouble(item[4]);
				Double deltaPower = curPower - lastPower;
				
				if (enTrigger) {
					triggerProcedurePowerList.add(curPower);
					if (triggerStablePowerList.size() >= Parameter.stablePowerListLength) {
						if (!triggerDetectionCheck(triggerProcedurePowerList.getFirst(), 
								triggerProcedurePowerList.getLast(), Parameter.triggerDeltaPower)) {
							stAppliance.pop();
							beforeLast = lastPower; lastPower = curPower;
							triggerProcedurePowerList.clear();
							triggerStablePowerList.clear(); enTrigger = false;
							continue;
						}
						Appliance lastTriggerApp = stAppliance.peek();
						Integer applianceID = findApplianceID(triggerProcedurePowerList);
						lastTriggerApp.setApplianceID(applianceID);
						lastTriggerApp.setPower(powerDatabaseTable.get(applianceID));
						eventJDBCTemplate.insert(applianceID, powerDatabaseTable.get(applianceID), 
								triggerProcedurePowerList.getLast()-triggerProcedurePowerList.getFirst(), 
								startTime, timestamp,  triggerProcedurePowerList.size(), listToString(triggerProcedurePowerList));
						DecimalFormat df = new DecimalFormat("#.00");
						bufDetect.write(startTime + "," + applianceID + "," + 
								df.format(Parameter.abs(triggerProcedurePowerList.getLast()-
										triggerProcedurePowerList.getFirst())) + "," + 
										triggerProcedurePowerList.size());
						bufDetect.newLine();
						triggerStablePowerList.clear();
						triggerProcedurePowerList.clear();
						enTrigger = false;
					} else if (triggerStablePowerList.size()<Parameter.stablePowerListLength
							&& Parameter.abs(deltaPower)<Parameter.triggerDeltaPower
							&& isTriggerStable(Parameter.stableDeltaPower, triggerStart, triggerStablePowerList)) {
						triggerStablePowerList.add(curPower);
					} else triggerStablePowerList.clear();
					
				} else if (deTrigger && !stAppliance.empty()) {
					triggerProcedurePowerList.add(curPower);
					if (triggerStablePowerList.size() >= Parameter.stablePowerListLength) {
						if (!triggerDetectionCheck(triggerProcedurePowerList.getFirst(), 
								triggerProcedurePowerList.getLast(), Parameter.triggerDeltaPower)) {
							beforeLast = lastPower; lastPower = curPower;
							triggerProcedurePowerList.clear();
							triggerStablePowerList.clear(); deTrigger = false;
							continue;
						}
						Integer applianceID = findApplianceID(triggerProcedurePowerList.getFirst(), 
								triggerProcedurePowerList.getLast(), stAppliance);
						for (Appliance app : stAppliance) {
							if (app.getApplianceID() == applianceID) {
								app.setEndTime(endTime);
								eventJDBCTemplate.insert(applianceID, powerDatabaseTable.get(applianceID), 
										triggerProcedurePowerList.getLast()-triggerProcedurePowerList.getFirst(), 
										endTime, timestamp, triggerProcedurePowerList.size(), listToString(triggerProcedurePowerList));
								DecimalFormat df = new DecimalFormat("#.00");
								bufDetect.write(endTime + "," + applianceID + "," + 
										df.format(Parameter.abs(triggerProcedurePowerList.getLast()-
												triggerProcedurePowerList.getFirst())) + "," + 
												triggerProcedurePowerList.size());
								bufDetect.newLine();
								triggerStablePowerList.clear();
								triggerProcedurePowerList.clear();
								stAppliance.remove(app); deTrigger = false;
								break;
							}
						}
					} else if (triggerStablePowerList.size()<Parameter.stablePowerListLength
							&& Parameter.abs(deltaPower)<Parameter.triggerDeltaPower
							&& isTriggerStable(Parameter.stableDeltaPower, curPower, triggerStablePowerList)) {
						triggerStablePowerList.add(curPower);
					} else triggerStablePowerList.clear();
					
				} else if (!enTrigger && Parameter.abs(deltaPower)>Parameter.triggerDeltaPower
						&& deltaPower>=0) {
					Appliance app = new Appliance();
					startTime = timestamp; app.setStartTime(startTime);
					stAppliance.push(app);
					triggerProcedurePowerList.add(beforeLast);
					triggerProcedurePowerList.add(lastPower);
					triggerProcedurePowerList.add(curPower);
					triggerStart = beforeLast; enTrigger = true;
					
				} else if (!deTrigger && Parameter.abs(deltaPower)>Parameter.triggerDeltaPower 
						&& deltaPower<0 && !stAppliance.empty()) {
					endTime = timestamp;
					triggerProcedurePowerList.add(beforeLast);
					triggerProcedurePowerList.add(lastPower);
					triggerProcedurePowerList.add(curPower);
					triggerStart = beforeLast; deTrigger = true;
				}
				
				beforeLast = lastPower;
				lastPower = curPower;
			}
			
			bufr.close();
			bufDetect.flush();
			bufDetect.close();
			
		} catch (Exception e) { e.printStackTrace(); }
	}
}
