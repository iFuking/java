package jp.co.worksap.intern.util;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import jp.co.worksap.intern.constants.Messages;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

/**
 * Miscellaneous utilities.
 * 
 */
public final class Utilities {

	private static final Log logger = LogFactory.getLog(Utilities.class);

	// Must not be instantiated
	private Utilities() {
	}

	/**
	 * close reader without any exception.
	 * 
	 * @param reader
	 */
	public static void closeSilently(FileReader reader) {
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				logger.fatal(Messages.ERROR_CSV_FILE_CLOSE, e);
			}
		}
	}

	/**
	 * close reader without any exception.
	 * 
	 * @param reader
	 */
	public static void closeSilently(CSVReader reader) {
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				logger.fatal(Messages.ERROR_CSV_FILE_CLOSE, e);
			}
		}
	}

	/**
	 * flush first, and close writer without any exception.
	 * 
	 * @param writer
	 */
	public static void closeSilently(CSVWriter writer) {
		if (writer != null) {
			try {
				writer.flush();
				writer.close();
			} catch (IOException e) {
				logger.fatal(Messages.ERROR_CSV_FILE_CLOSE, e);
			}
		}
	}

	/** default datetime format */
	private static final SimpleDateFormat datetimeFormat = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm");

	public static synchronized Date parseDateTimeStr(String datetimeStr)
			throws IllegalArgumentException {
		try {
			return datetimeFormat.parse(datetimeStr);
		} catch (ParseException e) {
			logger.fatal(Messages.ERROR_PARSE_DATE, e);
			throw new IllegalArgumentException("Failed to parse Date Time String!");
		}
	}
	
	public static synchronized String formatDateTime(Date date) {
		return datetimeFormat.format(date);
	}
	
	/** default date format */
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy/MM/dd");

	public static synchronized Date parseDateStr(String dateStr)
			throws IllegalArgumentException {
		try {
			return dateFormat.parse(dateStr);
		} catch (ParseException e) {
			logger.fatal(Messages.ERROR_PARSE_DATE, e);
			throw new IllegalArgumentException("Failed to parse Date String!");
		}
	}
	
	public static synchronized String formatDate(Date date) {
		return dateFormat.format(date);
	}

	/** default time format */
	public static final SimpleDateFormat timeFormat = new SimpleDateFormat(
			"HH:mm");

	public static synchronized Date parseTimeStr(String timeStr)
			throws IllegalArgumentException {
		try {
			return timeFormat.parse(timeStr);
		} catch (ParseException e) {
			logger.fatal(Messages.ERROR_PARSE_DATE, e);
			throw new IllegalArgumentException("Failed to parse Time String!");
		}
	}

	public static synchronized String formatTime(Date time) {
		return timeFormat.format(time);
	}
	
	
	
	private static final Scanner scanner = new Scanner(System.in);
	private static final String optionFormat = "#%s%d: %s";
	
	/**
	 * Read an integer, iterate until integer is valid
	 * 
	 * @author dirk
	 */
	public static Integer readInteger() {
		while (true) {
			String strValue = scanner.nextLine();
			Integer intValue;
			try {
				intValue = Integer.valueOf(strValue);
				return intValue;
			} catch (NumberFormatException e) {
				return -1;
			} 
		}
	}
	
	public static Integer readInputSelection(String name, String[] funcCandt) {
		for (int i = 0; i < funcCandt.length; ++i) {
			System.out.println(String.format(optionFormat, name, i+1, funcCandt[i]));
		}
		
		while (true) {
			System.out.println("\nPlease enter the "+name+" id: ");
			String strValue = scanner.nextLine();
			Integer intValue = -1;
			try {
				intValue = Integer.valueOf(strValue);
			} catch (NumberFormatException e) {
				return -1;
			}
			if (intValue<0 || intValue>funcCandt.length) {
				System.out.println("Invalid "+name+" id, check and try again.");
				continue;
			}
			return intValue;
		}
	}
	
}
