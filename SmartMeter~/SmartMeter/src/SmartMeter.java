
import java.io.*;
import java.util.*;

public class SmartMeter {
	
	private static final double deltaRate = 0.45;
	private static final String buildPowerDatabaseFile = 
			"E:\\zju\\pr\\smart meter\\BLUED_data\\dataset001-003_439events\\events_trigger_mean_power180_3.txt";
	private static final int interval = 180 + 60 + 180;
	private static final int triggerInterval = 60 + 180;
	private static final double[] powerConsumption = new double [BuildPowerDatabase.applianceNumbers];
	
	private double Abs(double x) {
		return x > 0 ? x : -x;
	}
	
	private double CalculateMeanPower(Queue<Double> power) {
		
		double triggerBeforePower = 0.0, triggerAfterPower = 0.0;
		int foo = 0;
		for (Double p : power) {
			if (foo < 180) triggerBeforePower += p;
			else if (foo >= 240 && foo < 420) triggerAfterPower += p;
			foo++;
		}
		
		triggerBeforePower /= 180;
		triggerAfterPower /= 180;
		double deltaMeanPower = Abs(triggerAfterPower - triggerBeforePower);
		
		return deltaMeanPower;
	}
	
	private int FindAppliance(Queue<Double> power, Stack<Appliance> st) {
		
		double deltaMeanPower = CalculateMeanPower(power);
		
		double diff = deltaMeanPower;  int labelId = -1;
		for (Appliance app : st) {
			if (Abs(deltaMeanPower-app.GetPower()) < diff) {
				diff = Abs(deltaMeanPower-app.GetPower());
				labelId = app.GetLabelId();
			}
		}
		return labelId;
	}
	
	private int FindAppliance(Queue<Double> power, HashMap<Integer, Double> powerTable) {
		
		double deltaMeanPower = CalculateMeanPower(power);
		
		double diff = deltaMeanPower; int flag = -1;
		for (int i = 0; i < powerTable.size(); i++) {
			if (Abs(deltaMeanPower-powerTable.get(i)) < diff)
				diff = Abs(deltaMeanPower-powerTable.get(flag=i));
		}
		return flag;
	}
	
	private void Process(String powerFile) {
		
		try {
			
			File readFile = new File(powerFile);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(readFile));
			BufferedReader bufr = new BufferedReader(reader);
			
			BuildPowerDatabase appliancePowerDatabase = new BuildPowerDatabase(buildPowerDatabaseFile);
			HashMap<Integer, Double> powerTable = appliancePowerDatabase.GetPowerDatabase();
			
			int index = 0, lastTrigger = -1;
			double lastPower = -1, curPower = -1;
			Stack<Appliance> st = new Stack<Appliance>();
			boolean enTrigger = false, deTrigger = false;
			Queue<Double> queue = new LinkedList<Double>();
			
			for (String line = bufr.readLine(); line != null; line = bufr.readLine(), index++) {
				if (index == 0) { 
					lastPower = Double.parseDouble(line);
					continue; 
				}
				curPower = Double.parseDouble(line);
				double deltaPower = curPower - lastPower;
				
				if (Abs(deltaPower)/lastPower>deltaRate && deltaPower>0
						&& (lastTrigger<0 || index-lastTrigger>triggerInterval)) {
					Appliance appliance = new Appliance(index);
					st.push(appliance);
					enTrigger = true; lastTrigger = index;
				} else if (Abs(deltaPower)/lastPower>deltaRate && deltaPower<0
						&& (lastTrigger<0 || index-lastTrigger>triggerInterval)) {
					deTrigger = true; lastTrigger = index;
				}
				
				if (enTrigger || deTrigger) {
					Appliance lastApp = st.peek(); int labelId = -1;
					if (enTrigger && 
							lastTrigger+triggerInterval-1<index) {
						labelId = FindAppliance(queue, powerTable);
						lastApp.SetLabelId(labelId);
						lastApp.SetPower(powerTable.get(labelId));
						enTrigger = false;
					} else if (deTrigger &&
							lastTrigger+triggerInterval-1<index) {
						labelId = FindAppliance(queue, st);
						for (Appliance app : st) {
							if (app.GetLabelId() == labelId) {
								app.SetEndIndex(index-triggerInterval);
								powerConsumption[app.GetLabelId()] += app.PowerConsumption();
								st.remove(app);  deTrigger = false;
								break;
							}
						}
					}
				}
				queue.offer(curPower);
				if (queue.size() > interval) queue.poll();
				lastPower = curPower;
				
				//for (int i = 0; i < BuildPowerDatabase.applianceNumbers; i++)
					// if (powerConsumption[i] > 0)
				System.out.println(powerConsumption[11]);
			}
			
			bufr.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}
	
	public static void main(String[] args) {
		
		SmartMeter obj = new SmartMeter();
		obj.Process("E:\\zju\\pr\\smart meter\\BLUED_data\\export_data\\exprt_data_pa001_3.txt");
		return;
	}
}
