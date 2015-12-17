package jp.co.worksap.intern.entities;

import java.io.FileReader;
import java.io.IOException;

import jp.co.worksap.intern.constants.Constants;

import org.junit.Test;

import au.com.bytecode.opencsv.CSVReader;

public class RawCSVReaderTest {
	private static final String ADDRESS_FILE = Constants.DEFAULT_CSV_FOLDER
			+ "TOURS.csv";

	@Test
	public void readTourTable() throws IOException {

		@SuppressWarnings("resource")
		CSVReader reader = new CSVReader(new FileReader(ADDRESS_FILE),
				Constants.DEFAULT_CSV_SEPARATOR);
		String[] nextLine;
		boolean isTitleLine = true;
		int index = 1;
		while ((nextLine = reader.readNext()) != null) {
			if (isTitleLine) {
				System.out.println("TOUR Table Columns : ["
						+ nextLine[0] + ", " + nextLine[1] + "," + nextLine[2]
						+ "," + nextLine[3] + ", " + nextLine[4] + "]");
				isTitleLine = false;
			} else {
				System.out.println("TOUR Record: #" + (index++)
						+ "\nTOUR ID: [" + nextLine[0] + "]\nTOUR MST ID: ["
						+ nextLine[1] + "]\nStart Date: [" + nextLine[2]
						+ "]\nExpected Price: [" + nextLine[3]
						+ "]\nPrice Unit: [" + nextLine[4] + "]\n");
			}
		}
	}
}
