package com.zju.chmwang;

import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

public interface EventDAO {
	public void setDataSource(DataSource ds);
	public void insert(Integer applianceID, Double appliancePower, Double triggerProcedureDeltaPower, 
			Timestamp startTime, Timestamp endTime, 
			Integer triggerProdurePowerNumbers, String triggerProcedurePowerList);
	public List<Event> listEvents();
}
