package jp.co.worksap.intern.entities.report;

import jp.co.worksap.intern.entities.ICsvMasterDTO;

public class ReportDTO implements ICsvMasterDTO {

	private static final long serialVersionUID = -5538243149394207296L;
	
	private String type;
	private Integer lastValue;
	private Integer currValue;
	private String lastPercentage;
	private String currPercentage;
	private String growth;
	
	private static final String[] headers = new String[] {
			"type", "lastValue", "currValue", 
			"lastPercentage", "currPercentage", "growth"
		};
	
	public ReportDTO(String type, Integer lastValue, Integer currValue,
			String lastPercentage, String currPercentage, String growth) {
		this.type = type;
		this.lastValue = lastValue;
		this.currValue = currValue;
		this.lastPercentage = lastPercentage;
		this.currPercentage = currPercentage;
		this.growth = growth;
	}
	
	public String getType() {
		return type;
	}
	
	public Integer getLastValue() {
		return lastValue;
	}
	
	public Integer getCurrValue() {
		return currValue;
	}
	
	public String getLastPercentage() {
		return lastPercentage;
	}
	
	public String getCurrPercentage() {
		return currPercentage;
	}
	
	public String getGrowth() {
		return growth;
	}
	
	
	public String[] toCSVStringArray() {
		String[] csvStr = new String[headers.length];
		csvStr[0] = type;
		csvStr[1] = Integer.toString(lastValue);
		csvStr[2] = Integer.toString(currValue);
		csvStr[3] = lastPercentage;
		csvStr[4] = currPercentage;
		csvStr[5] = growth;
		return csvStr;
	}
	
	public String[] toCSVHeaders() {
		return headers;
	}
}
