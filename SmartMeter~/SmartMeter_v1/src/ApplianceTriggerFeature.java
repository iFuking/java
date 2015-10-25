
import java.util.LinkedList;

public class ApplianceTriggerFeature {

	private int eventsId;
	private int labelId;
	private LinkedList<Double> triggerPowerList = new LinkedList<Double>();
	
	public ApplianceTriggerFeature() {
		
	}
	
	public void SetTriggerPowerList(double power) {
		
		triggerPowerList.add(power);
		return;
	}
	
 	public LinkedList<Double> GetTriggerPowerList() {
		return triggerPowerList;
	}
	
	public int GetTriggerPowerListLength() {
		return triggerPowerList.size();
	}
}
