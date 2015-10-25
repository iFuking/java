import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;


public class CheckEventsDetection {

	public CheckEventsDetection() {
		
		try {
			
			File detectFile = new File("e:\\foo4.txt");
			InputStreamReader detectReader = new InputStreamReader(new FileInputStream(detectFile));
			BufferedReader detectBuf = new BufferedReader(detectReader);
			
			File rawFile = new File("E:\\zju\\pr\\smart meter\\BLUED_data\\data_analysis\\app_power_pa4.txt");
			InputStreamReader rawReader = new InputStreamReader(new FileInputStream(rawFile));
			BufferedReader rawBuf = new BufferedReader(rawReader);
			
			int eventsNumbers = 155, lines = 0;
			int[] rawIndex = new int[eventsNumbers];
			for (String rawLine = rawBuf.readLine(); rawLine != null; rawLine = rawBuf.readLine()) {
				String[] rawItem = rawLine.split(" ");
				int index = Integer.parseInt(rawItem[0]);
				rawIndex[lines++] = index;
			}
			rawBuf.close();
			
			boolean[] visit = new boolean[eventsNumbers];
			int matchEventsNumbers = 0;
			for (String detectLine = detectBuf.readLine(); detectLine != null; detectLine = detectBuf.readLine()) {
				String[] detectItem = detectLine.split(" ");
				int detectIndex = Integer.parseInt(detectItem[0]);
				for (int i = 0; i < lines; i++) {
					if (!visit[i] && Abs(detectIndex-rawIndex[i])<5) {
						visit[i] = true; 
						matchEventsNumbers++; break;
					}
				}
			}
			System.out.println(matchEventsNumbers + "¡¡" + lines);
			detectBuf.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	private int Abs(int x) {
		return x > 0 ? x : -x;
	}
}
