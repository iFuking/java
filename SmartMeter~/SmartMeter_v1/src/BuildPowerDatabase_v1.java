
import java.io.*;
import java.util.*;

public class BuildPowerDatabase_v1 {

	// public static final double 
	private static final String powerDatabaseFile = 
			"E:\\zju\\pr\\smart meter\\BLUED_data\\data_analysis\\app_power_all.txt";
	private static final double[] powerDatabase = new double [Conf.applianceNumbers];
	private HashMap<Integer, Double> powerDatabaseTable = new HashMap<Integer, Double>();
	
	public BuildPowerDatabase_v1() {
		
		CreatePowerDatabaseTable();
	}

	
	public HashMap<Integer, Double> GetPowerDatabase() {
		return powerDatabaseTable;
	}
	
	
	private void CreatePowerDatabaseTable() {
		
		try {
			
			File readFile = new File(powerDatabaseFile);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(readFile));
			BufferedReader bufr = new BufferedReader(reader);
			
			int[] cnt = new int[Conf.applianceNumbers];
			for (String line = bufr.readLine(); line != null; line = bufr.readLine()) {
				String[] item = line.split(" ");
				powerDatabase[Integer.parseInt(item[1])] += Double.parseDouble(item[2]);
				cnt[Integer.parseInt(item[1])]++;
			}
			bufr.close();
			
			for (int i = 0; i < powerDatabase.length; i++) {
				if (cnt[i] > 0) {
					powerDatabase[i] /= cnt[i];
					powerDatabaseTable.put(i, powerDatabase[i]);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}
	
	
	/*public static void main(String[] args) {
		BuildPowerDatabase_v1 foo = new BuildPowerDatabase_v1();
		return;
	}*/
}
