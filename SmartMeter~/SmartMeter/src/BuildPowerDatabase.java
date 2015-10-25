
import java.io.*;
import java.text.*;
import java.util.*;

public class BuildPowerDatabase {

	public static final int applianceNumbers = 60;
	private static final double[] database = new double [applianceNumbers];
	private HashMap<Integer, Double> hashTable = new HashMap<Integer, Double>();
	
	public BuildPowerDatabase() {
		
	}
	
	public BuildPowerDatabase(String powerMeanFile) {
		
		try {
			
			File readFile = new File(powerMeanFile);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(readFile));
			BufferedReader bufr = new BufferedReader(reader);
			
			int[] cnt = new int [applianceNumbers];
			for (String line = bufr.readLine(); line != null; line = bufr.readLine()) {
				String[] item = line.split(" ");
				database[Integer.parseInt(item[0])] += Double.parseDouble(item[1]);
				cnt[Integer.parseInt(item[0])]++;
			}
			bufr.close();
			
			for (int i = 0; i < database.length; i++) {
				if (cnt[i] > 0) database[i] /= cnt[i];
				hashTable.put(i, database[i]);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public HashMap<Integer, Double> GetPowerDatabase() {
		return hashTable;
	}
	
}
