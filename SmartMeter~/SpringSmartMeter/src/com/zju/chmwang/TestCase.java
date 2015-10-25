package com.zju.chmwang;

/*
create table Event(
	eventID int not null auto_increment, 
	applianceID int not null, 
	appliancePower double not null, 
	triggerProcedureDeltaPower double not null, 
	startTime datetime not null, 
	endTime datetime not null, 
	triggerProcedurePowerNumbers int not null, 
	triggerProcedurePowerList varchar(512) not null, 
	primary key (eventID)
);
 */

public class TestCase {
	public static void main(String[] args) {
		SmartMeter obj = new SmartMeter();
		obj.solve(Parameter.powerFile);
		return;
	}
}
