
public class Appliance {

	private static final double timestampUnit = 1.0 / 60;
	private int labelId;
	private double power;
	private int startIndex;
	private int endIndex;
	private double workingTime;
	
	public Appliance() {
		
	}
	
	public Appliance(int startIndex) {
		
		labelId = -1;
		power = -1;
		this.startIndex = startIndex;	
		endIndex = -1;
	}
	
	public int GetLabelId() {
		return labelId;
	}
	
	public double GetPower() {
		return power;
	}
	
	public int GetStartIndex() {
		return startIndex;
	}
	
	public int GetEndIndex() {
		return endIndex;
	}
	
	public void SetLabelId(int labelId) {
		this.labelId = labelId;
		return;
	}
	
	public void SetPower(double power) {
		this.power = power;
		return;
	}
	
	public void SetEndIndex(int endIndex) {
		this.endIndex = endIndex;
		return;
	}
	
	private void SetWorkingTime() {
		workingTime = timestampUnit * (endIndex-startIndex) / 3600;
		return;
	}
	
	public double PowerConsumption() {
		SetWorkingTime();
		return power * workingTime;
	}
}
