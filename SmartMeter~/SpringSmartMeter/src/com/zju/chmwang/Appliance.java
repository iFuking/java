package com.zju.chmwang;

import java.sql.Timestamp;

public class Appliance {
	private Integer applianceID;
	private Timestamp startTime;
	private Timestamp endTime;
	private Double power;
	
	public void setApplianceID(Integer applianceID) {
		this.applianceID = applianceID;
	}
	public Integer getApplianceID() {
		return applianceID;
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
	
	public void setPower(Double power) {
		this.power = power;
	}
	public Double getPower() {
		return power;
	}
}
