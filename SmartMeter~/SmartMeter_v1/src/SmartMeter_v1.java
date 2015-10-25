
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.Map.Entry;

public class SmartMeter_v1 {
	
	private static final String powerPath = 
			"E:\\zju\\pr\\smart meter\\BLUED_data\\export_data\\exprt_data_p";
	private static final String[] phase = {"a", "b"};
	private static final double[] consumptionDead = new double [Conf.applianceNumbers];
	
	public static double Abs(double x) {
		return x > 0 ? x : -x;
	}
	
	private int FindApplianceLabelId(double triggerPower, double stablePower, HashMap<Integer, Double> powerTable) {
		
		double deltaPower = Abs(triggerPower - stablePower);
		
		double diff = 0x7fffffff * 1.0;  int labelId = 0;
		Iterator<Entry<Integer, Double>> iter = powerTable.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<Integer, Double> entry = iter.next();
			int key = entry.getKey();
			double val = entry.getValue();
			if (Abs(deltaPower - val) < diff) {
				diff = Abs(deltaPower - val);
				labelId = key;
			}
		}
		return labelId;
	}
	
	/*private LinkedList<Integer> FindApplianceLabelId(double triggerPower, double stablePower, HashMap<Integer, Double> powerTable) {
		
		double deltaPower = Abs(triggerPower - stablePower);
		double fluctuationRate = 0.1;
		
		double diff = deltaPower*fluctuationRate;
		LinkedList<Integer> labelIdList = new LinkedList<Integer>();
		Iterator<Entry<Integer, Double>> iter = powerTable.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<Integer, Double> entry = iter.next();
			int key = entry.getKey();
			double val = entry.getValue();
			if (Abs(deltaPower - val) < diff) {
				labelIdList.add(key);
			}
		}
		return labelIdList;
	}*/
	
	private int FindApplianceLabelId(double triggerPower, double stablePower, Stack<Appliance_v1> st) {
		
		double deltaPower = Abs(triggerPower - stablePower);
		
		double diff = 0x7fffffff * 1.0;  int labelId = 0;
		for (Appliance_v1 app : st) {
			if (Abs(deltaPower - app.GetPower()) < diff) {
				diff = Abs(deltaPower - app.GetPower());
				labelId = app.GetLabelId();
			}
		}
		return labelId;
	}
	
	private boolean EnStable(LinkedList<Double> llist, double power) {
		
		if (llist.size() < 2) return true;
		
		double deltaSum = 0.0;
		for (int i = 0; i < llist.size()-1; i++) {
			deltaSum += (llist.get(i+1) - llist.get(i));
			if (Abs(deltaSum)/power >= Conf.deltaRate) return false;
		}
		return true;
	}
	
	private void Process(String path, int fileId, int ph) {
		
		try {
			
			File readFile = new File(path + phase[ph] + fileId);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(readFile));
			BufferedReader bufr = new BufferedReader(reader);
			
			File writeFile = new File("e:\\foo1.txt");
			writeFile.createNewFile();
			BufferedWriter bufw = new BufferedWriter(new FileWriter(writeFile));
			
			BuildPowerDatabase_v1 appliancePowerDatabase = new BuildPowerDatabase_v1();
			HashMap<Integer, Double> powerTable = appliancePowerDatabase.GetPowerDatabase();
			
			int index = 0, startIndex = 0, endIndex = 0;
			double beforeLast = -1.0, lastPower = -1.0, curPower = -1.0, triggerPower = -1.0;
			Stack<Appliance_v1> st = new Stack<Appliance_v1>();
			boolean enTrigger = false, deTrigger = false;
			LinkedList<Double> llist = new LinkedList<Double>();
			Stack<ApplianceTriggerFeature> applianceTriggerFeature = new Stack<ApplianceTriggerFeature>();
			
			for (String line = bufr.readLine(); line != null; line = bufr.readLine(), ++index) {
				
				if (index == 0) {
					beforeLast = Double.parseDouble(line);
					continue;
				} else if (index == 1) {
					lastPower = Double.parseDouble(line);
					continue;
				}
				
				double[] consumptionAlive = new double [Conf.applianceNumbers];
				if (index % 60 == 0) {
					if (!st.empty()) {
						for (Appliance_v1 app : st) {
							app.SetCurIndex(index);
							consumptionAlive[app.GetLabelId()] += app.CurPowerConsumption();
						}
					}
					// System.out.println(consumptionAlive[11]+consumptionDead[11]);
					/*DecimalFormat df = new DecimalFormat("#.000000");
					bufw.write(df.format(consumptionAlive[11]+consumptionDead[11]));
					bufw.newLine();*/
				}
				
				curPower = Double.parseDouble(line);
				double deltaPower = curPower - lastPower;	
				
				if (enTrigger) {
					
					/*ApplianceTriggerFeature triggerFeature = applianceTriggerFeature.pop();
					triggerFeature.SetTriggerPowerList(curPower);*/
					
					if (llist.size() >= Conf.stablePowerListLength) {
						Appliance_v1 lastApp = st.peek();
						//LinkedList<Integer> labelIdList = FindApplianceLabelId(triggerPower, llist.getLast(), powerTable);
						int labelId = FindApplianceLabelId(triggerPower, llist.getLast(), powerTable);
						llist.clear(); 
						lastApp.SetLabelId(labelId);
						lastApp.SetPower(powerTable.get(labelId));
						DecimalFormat df = new DecimalFormat("#.000000");
						bufw.write(lastApp.GetStartIndex() + " " +
								lastApp.GetLabelId() + " " + df.format(lastApp.GetPower()));
						bufw.newLine();
						enTrigger = false;
						// applianceTriggerFeature.push(triggerFeature);
					} else if (llist.size()<Conf.stablePowerListLength &&
							Abs(deltaPower)/triggerPower<Conf.deltaRate && 
							EnStable(llist, triggerPower)) 
						llist.add(curPower);
					else llist.clear(); 
					
				} else if (deTrigger && !st.empty()) {
					
					if (llist.size() >= Conf.stablePowerListLength) {
						int labelId = FindApplianceLabelId(triggerPower, llist.getLast(), st);
						llist.clear();
						for (Appliance_v1 app : st) {
							if (labelId == app.GetLabelId()) {
								app.SetEndIndex(endIndex);
								consumptionDead[app.GetLabelId()] += app.TotalPowerConsumption();
								DecimalFormat df = new DecimalFormat("#.000000");
								bufw.write(app.GetEndIndex() + " " +
										app.GetLabelId() + " " + df.format(app.GetPower()));
								bufw.newLine();
								st.remove(app); deTrigger = false;
								break;
							}
						}
					} else if (llist.size()<Conf.stablePowerListLength &&
							Abs(deltaPower)/curPower<Conf.deltaRate && 
							EnStable(llist, curPower)) 
						llist.add(curPower);
					else llist.clear();
					
				} else if (!enTrigger && Abs(deltaPower)/lastPower>Conf.deltaRate 
						&& deltaPower>=0) {
					
					Appliance_v1 app = new Appliance_v1(startIndex=index-1);
					st.push(app); 
					triggerPower = beforeLast; enTrigger = true;
					
					/*ApplianceTriggerFeature triggerFeature = new ApplianceTriggerFeature();
					triggerFeature.SetTriggerPowerList(beforeLast);
					triggerFeature.SetTriggerPowerList(lastPower);
					triggerFeature.SetTriggerPowerList(curPower);
					applianceTriggerFeature.push(triggerFeature);*/
					
				} else if (!deTrigger && Abs(deltaPower)/curPower>Conf.deltaRate 
						&& deltaPower<0 && !st.empty()){
					
					endIndex = index - 1;
					triggerPower = beforeLast; deTrigger = true;
					
				} 
				
				beforeLast = lastPower;
				lastPower = curPower;
			}
			
			bufr.close();
			bufw.flush();
			bufw.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}
	
	public static void main(String[] args) {
		
		for (int i = 1; i <= 4; i++) {
			for (int j = 0; j < 1; j++) {
				SmartMeter_v1 obj = new SmartMeter_v1();
				obj.Process(powerPath, i, j);
			}
		}
		return;
	}
}
