package com.zju.chmwang;

public class Parameter {
	public static final Integer INF = 0x7fffffff;
	public static final Integer stablePowerListLength = 3;
	public static final Integer frequency = 60;
	public static final Integer timeInterval = 60;
	
	public static final Double timestampUnit = 1.0 / frequency;
	public static final Double tolerateFluctuationRate = 0.05;
	public static final Double triggerDeltaPower = 500.0;
	public static final Double stableDeltaPower = 300.0;
	
	public static final String powerDatabaseFile = 
			"E:\\zju\\pr\\smart meter\\BLUED_data\\me_watt\\appliance_description.txt";
	public static final String powerFile = 
			"E:\\zju\\pr\\smart meter\\BLUED_data\\me_watt\\me-watt_from_June1st.txt";
	public static final String eventDetectFile = 
			"E:\\zju\\pr\\smart meter\\BLUED_data\\me_watt\\event_detect.txt";
	
	
	public static Integer abs(Integer x) {
		return x > 0 ? x : -x;
	}
	
	public static Double abs(Double x) {
		return x > 0 ? x : -x;
	}
}