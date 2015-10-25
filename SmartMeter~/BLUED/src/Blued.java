/*
 Description: 
 (1) Analysis datasets BLUED, one record of datasets' file is as follows: 
 	blablabla...
 	X_Value,Current A,Current B,VoltageA,Comment
 	0.000750,1.230355,-1.569521,-108.787669
 	0.000833,1.230355,-1.569521,-103.927891
 	0.000917,1.251582,-1.569521,-99.099670
 	0.001000,1.272809,-1.527350,-94.397677
 	blablabla...
 	Calculate powerA = CurrentA * VoltageA, powerB = CurrentB * VoltageB, VoltageB = -VoltageA
 (2) Basic steps of file operation in JAVA. 
 Author: Chenmin Wang. 
 Date: 2015/04/24
 */

import java.io.*;
import java.text.*;

public class Blued {
	
	private static final String[] DATA_HEADER = {"X_Value", "Current A", "Current B", 
		"VoltageA", "Comment"};
	
	/*private static boolean IsHeader(String[] item) {
		for (int i = 0; i < DATA_HEADER.length; i++)
			if (item[i] != DATA_HEADER[i]) return false;
		return true;
	}*/
	
	public static void main(String[] args) {
		try {
			
			// three steps of file read
			File readname = new File(args[0]);
			// build a input stream object 'reader'
			InputStreamReader reader = new InputStreamReader(new FileInputStream(readname));
			// build a object, which make computer understand the file content
			BufferedReader bufrd = new BufferedReader(reader);
			
			// write file
			File writename = new File(args[1]);
			writename.createNewFile();
			BufferedWriter bufwt = new BufferedWriter(new FileWriter(writename));
			
			// find 'End of Header', load data
			for (String line = bufrd.readLine(); line != null; line = bufrd.readLine()) {
				String[] item = line.split(",");

				// ignore 'End of Header' above
				if (item.length >= DATA_HEADER.length) break;
				// if (!IsHeader(item)) continue;
			}
			
			// write header
			bufwt.write("Timestamp,PowerA,PowerB");
			bufwt.newLine();
			
			for (String line = bufrd.readLine(); line != null; line = bufrd.readLine()) {
				String[] item = line.split(",");
				
				// load data
				Record record = new Record(item);
				
				DecimalFormat df = new DecimalFormat("#.000000");
				bufwt.write(record.Timestamp() + "," + df.format(record.PowerA()) + "," + df.format(record.PowerB()));
				bufwt.newLine();
			}
			
			bufwt.flush();
			bufwt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}
}
