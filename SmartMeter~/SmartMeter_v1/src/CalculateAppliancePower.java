
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Stack;

public class CalculateAppliancePower {
	
	private static final String powerPath = 
			"E:\\zju\\pr\\smart meter\\BLUED_data\\export_data\\exprt_data_p";
	private static final String eventsIndexPath = 
			"E:\\zju\\pr\\smart meter\\BLUED_data\\export_data\\events_index_p";
	private static final String appliancePowerPath = 
			"E:\\zju\\pr\\smart meter\\BLUED_data\\data_analysis\\app_power_p";
	private static final String[] phase = {"a", "b"};
	

	public CalculateAppliancePower() {
		
		try {
			
			for (int i = 1; i <= 4; i++) {  // XXX_1.mat ~ XXX_4.mat
				for (int j = 0; j < 2; j++) {  // phase A and B
					String powerFile = powerPath + phase[j] + i + ".txt";
					File readPowerFile = new File(powerFile);
					InputStreamReader powerReader = new InputStreamReader(new FileInputStream(readPowerFile));
					BufferedReader bufPower = new BufferedReader(powerReader);
					
					String eventsIndexFile = eventsIndexPath + phase[j] + i + ".txt";
					File readIndexFile = new File(eventsIndexFile);
					InputStreamReader eventsIndexReader = new InputStreamReader(new FileInputStream(readIndexFile));
					BufferedReader bufEventsIndex = new BufferedReader(eventsIndexReader);
					
					String appliancePowerFile = appliancePowerPath + phase[j] + i + ".txt";
					File writeFile = new File(appliancePowerFile);
					writeFile.createNewFile();
					BufferedWriter bufw = new BufferedWriter(new FileWriter(writeFile));
					
					LinkedList<String> eventsIndexList = new LinkedList<String>();
					for (String line = bufEventsIndex.readLine(); line != null; line = bufEventsIndex.readLine()) {
						eventsIndexList.add(line);
					}
					int[] index = new int[eventsIndexList.size()];
					int[] label = new int[eventsIndexList.size()];
					int k = 0;
					for (String line : eventsIndexList) {
						String[] item = line.split(" ");
						index[k] = Integer.parseInt(item[0]);
						label[k] = Integer.parseInt(item[1]);
						k++;
					}
					
					int iter = 0, startIndex = 0; k = 0;
					double beforeLast = 0, lastPower = 0, curPower = 0;
					double startPower = -1, power = -1;
					Stack<Double> stTriggerPower = new Stack<Double>();
					for (String line = bufPower.readLine(); line != null; line = bufPower.readLine(), iter++) {
						curPower = Double.parseDouble(line);
						if (iter == 0) {
							beforeLast = Double.parseDouble(line);
							continue;
						} else if (iter == 1) {
							lastPower = Double.parseDouble(line);
							continue;
						} else if (k == eventsIndexList.size() || 
								(startPower<0 && iter!=index[k]-1)) {
							if (k < eventsIndexList.size())
								if (iter > index[k]-1) k++;
							beforeLast = lastPower;
							lastPower = curPower;
							continue;
						}
						
						if (iter == index[k]-1) {
							startIndex = iter;
							startPower = beforeLast;
						}
						
						double deltaPower = curPower-lastPower;
						if (stTriggerPower.size() >= Conf.stablePowerListLength) {
							power = SmartMeter_v1.Abs(stTriggerPower.peek() - startPower);
							DecimalFormat df = new DecimalFormat("#.000000");
							bufw.write(startIndex + " " + label[k] + " " + df.format(power));
							bufw.newLine();
							stTriggerPower.clear();
							startPower = -1; k++;
						} else if (stTriggerPower.size()<Conf.stablePowerListLength
								&& SmartMeter_v1.Abs(deltaPower)/lastPower<Conf.deltaRate 
								&& Stable(stTriggerPower)) {
							stTriggerPower.push(curPower);
						} else stTriggerPower.clear();
						
						beforeLast = lastPower;
						lastPower = curPower;
					}
					
					bufPower.close();
					bufEventsIndex.close();
					bufw.flush();
					bufw.close();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private boolean Stable(Stack<Double> st) {
		
		if (st.size() < 2) return true;
		
		double deltaSum = 0.0;
		for (int i = 0; i < st.size()-1; i++) {
			deltaSum += (st.get(i+1) - st.get(i));
			if (SmartMeter_v1.Abs(deltaSum)/st.peek() >= Conf.deltaRate) return false;
		}
		return true;
	}
}
