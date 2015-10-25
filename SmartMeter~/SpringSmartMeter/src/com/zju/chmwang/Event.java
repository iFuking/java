package com.zju.chmwang;

import java.sql.Timestamp;

public class Event {
	private Integer eventID;
	private Integer applianceID;
	private Double appliancePower;
	private Double triggerProcedureDeltaPower;
	private Timestamp startTime;
	private Timestamp endTime;
	private Integer triggerProcedurePowerNumbers;
	private String triggerProcedurePowerList;
	
	public void setEventID(Integer eventID) {
		this.eventID = eventID;
	}
	public Integer getEventID() {
		return eventID;
	}
	
	public void setApplianceID(Integer applianceID) {
		this.applianceID = applianceID;
	}
	public Integer getApplianceID() {
		return applianceID;
	}
	
	public void setAppliancePower(Double appliancePower) {
		this.appliancePower = appliancePower;
	}
	public Double getAppliancePower() {
		return appliancePower;
	}
	
	public void setTriggerProcedureDeltaPower(Double triggerProcedureDeltaPower) {
		this.triggerProcedureDeltaPower = triggerProcedureDeltaPower;
	}
	public Double setTriggerProcedureDeltaPower() {
		return triggerProcedureDeltaPower;
	}
	
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
	public Timestamp getEndTime() {
		return endTime;
	}
	
	public void setTriggerProcedurePowerNumbers(Integer triggerProcedurePowerNumbers) {
		this.triggerProcedurePowerNumbers = triggerProcedurePowerNumbers;
	}
	public Integer getTriggerProcedurePowerNumbers() {
		return triggerProcedurePowerNumbers;
	}
	
	public void setTriggerProcedurePowerList(String triggerProcedurePowerList) {
		this.triggerProcedurePowerList = triggerProcedurePowerList;
	}
	public String getTriggerProcedurePowerList() {
		return triggerProcedurePowerList;
	}
}
