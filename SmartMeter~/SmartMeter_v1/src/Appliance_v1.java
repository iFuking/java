
public class Appliance_v1 {

	private static final double timestampUnit = 1.0 / 60;
	private int labelId;
	private double power;
	private int startIndex;
	private int endIndex;
	private int curIndex;
	
	public Appliance_v1() {
		
	}
	
	public Appliance_v1(int startIndex) {
		
		this.startIndex = startIndex;
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
	
	public void SetCurIndex(int curIndex) {
		this.curIndex = curIndex;
		return;
	}
	
	public void SetEndIndex(int endIndex) {
		this.endIndex = endIndex;
		return;
	}
	
	private double WorkingTime(int index) {
		return timestampUnit * (index-startIndex) / 3600;
	}
	
	public double CurPowerConsumption() {
		return power * WorkingTime(curIndex);
	}
	
	public double TotalPowerConsumption() {
		return power * WorkingTime(endIndex);
	}
	
}
