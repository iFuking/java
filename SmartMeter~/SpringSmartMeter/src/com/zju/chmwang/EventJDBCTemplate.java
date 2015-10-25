package com.zju.chmwang;

import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class EventJDBCTemplate implements EventDAO {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	    this.jdbcTemplateObject = new JdbcTemplate(this.dataSource);
	}
	
	public void insert(Integer applianceID, Double appliancePower, Double triggerProcedureDeltaPower, 
			Timestamp startTime, Timestamp endTime, 
			Integer triggerProcedurePowerNumbers, String triggerProcedurePowerList) {
		String SQL = 
			"insert into Event (applianceID, appliancePower, triggerProcedureDeltaPower, startTime, endTime, triggerProcedurePowerNumbers, triggerProcedurePowerList) values (?, ?, ?, ?, ?, ?, ?)";
		jdbcTemplateObject.update(SQL, applianceID, appliancePower, triggerProcedureDeltaPower, 
				startTime, endTime, triggerProcedurePowerNumbers, triggerProcedurePowerList);
		return;
	}
	
	public List<Event> listEvents() {
		String SQL = "select * from Event";
		List<Event> events = jdbcTemplateObject.query(SQL, new EventMapper());
		return events;
	}
}
