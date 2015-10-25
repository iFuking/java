
public class Record {
	
	private String X_Value;  // current timestamp minus start timestamp
	private double CurrentA; // current on phase A
	private double CurrentB; // current on phase B
	private double VoltageA; // voltage on phase A
	
	public Record() {
		
	}
	
	public Record(String[] str) {
		
		this.X_Value = str[0];
		this.CurrentA = Double.parseDouble(str[1]);
		this.CurrentB = Double.parseDouble(str[2]);
		this.VoltageA = Double.parseDouble(str[3]);
	}
	
	private double VoltageB() {
		return 0 - VoltageA;
	}
	
	public String Timestamp() {
		return X_Value;
	}
	
	public double PowerA() {
		return CurrentA * VoltageA;
	}
	
	public double PowerB() {
		return CurrentB * VoltageB();
	}
}
