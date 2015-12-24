package jp.co.worksap.intern.writer;

import java.util.List;
import jp.co.worksap.intern.entities.ICsvMasterDTO;

/**
 * Result writer interface which output result to CSV file.
 * 
 */
public interface IResultWriter {
	
	/**
	 * Write results to output CSV file.
	 * @param result output results including header columns
	 */
	public void writeResult(List<String[]> result);
	
	public void writeCsvResult(ICsvMasterDTO[] result);
}
