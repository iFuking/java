import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Stack;



public class TriggerIntervalPercentage {

	private static final String powerPath = 
			"E:\\zju\\pr\\smart meter\\BLUED_data\\export_data\\exprt_data_p";
	private static final String eventsIndexPath = 
			"E:\\zju\\pr\\smart meter\\BLUED_data\\export_data\\events_index_p";
	private static final String[] phase = {"a", "b"};
	

	public TriggerIntervalPercentage() {
		
		try {
			
			int triggerIntervalLength = 0, iteration = 0;
			for (int i = 1; i <= 4; i++) {  // XXX_1.mat ~ XXX_4.mat
				for (int j = 0; j < 1; j++) {  // phase A and B
					
					String powerFile = powerPath + phase[j] + i + ".txt";
					File readPowerFile = new File(powerFile);
					InputStreamReader powerReader = new InputStreamReader(new FileInputStream(readPowerFile));
					BufferedReader bufPower = new BufferedReader(powerReader);
					
					String eventsIndexFile = eventsIndexPath + phase[j] + i + ".txt";
					File readIndexFile = new File(eventsIndexFile);
					InputStreamReader eventsIndexReader = new InputStreamReader(new FileInputStream(readIndexFile));
					BufferedReader bufEventsIndex = new BufferedReader(eventsIndexReader);
					
					LinkedList<String> eventsIndexList = new LinkedList<String>();
					for (String line = bufEventsIndex.readLine(); line != null; line = bufEventsIndex.readLine()) {
						eventsIndexList.add(line);
					}
					int[] index = new int[eventsIndexList.size()];
					int k = 0;
					for (String line : eventsIndexList) {
						String[] item = line.split(" ");
						index[k++] = Integer.parseInt(item[0]);
					}
					
					int iter = 0, startIndex = 0; k = 0;
					double beforeLast = 0, lastPower = 0, curPower = 0;
					double startPower = -1;
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
							if (k < eventsIndexList.size() && 
									iter > index[k]-1) k++;
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
							triggerIntervalLength += (iter-startIndex-Conf.stablePowerListLength);
							stTriggerPower.clear();
							startPower = -1; k++;
						} else if (stTriggerPower.size()<Conf.stablePowerListLength
								&& SmartMeter_v1.Abs(deltaPower)/lastPower<Conf.deltaRate 
								&& Stable(stTriggerPower, startPower)) {
							stTriggerPower.push(curPower);
						} else stTriggerPower.clear();
						
						beforeLast = lastPower;
						lastPower = curPower;
					}
					iteration += iter;
					
					bufPower.close();
					bufEventsIndex.close();
				}
			}
			System.out.println(triggerIntervalLength + " " + iteration);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	
	private boolean Stable(Stack<Double> st, double startPower) {
		
		if (st.size() < 2) return true;
		
		double deltaSum = 0.0;
		for (int i = 0; i < st.size()-1; i++) {
			deltaSum += (st.get(i+1) - st.get(i));
			if (SmartMeter_v1.Abs(deltaSum)/startPower >= Conf.deltaRate) return false;
		}
		return true;
	}
	
	
	public static void main(String[] args) {
		TriggerIntervalPercentage obj = new TriggerIntervalPercentage();
		return;
	}
}
