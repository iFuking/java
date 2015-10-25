package com.zju.chmwang;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class BuildPowerDatabase {
	private HashMap<Integer, Double> powerDatabaseTable = new HashMap<Integer, Double>();
	
	public BuildPowerDatabase() {
		buildPowerDatabaseByApplianceInfo();
	}
	
	private void buildPowerDatabaseByApplianceInfo() {
		try {
			File readFile = new File(Parameter.powerDatabaseFile);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(readFile));
			BufferedReader bufr = new BufferedReader(reader);
			
			for (String line = bufr.readLine(); line != null; line = bufr.readLine()) {
				String[] item = line.split(",");
				Integer key = Integer.parseInt(item[0]);
				Double value = Double.parseDouble(item[2]);
				powerDatabaseTable.put(key, value);
			}
			bufr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public HashMap<Integer, Double> getPowerDatabase() {
		return powerDatabaseTable;
	}
}
