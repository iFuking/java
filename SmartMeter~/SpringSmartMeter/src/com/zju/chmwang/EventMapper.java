package com.zju.chmwang;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class EventMapper implements RowMapper<Event> {
	public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
		Event event = new Event();
		event.setEventID(rs.getInt("eventID"));
		event.setApplianceID(rs.getInt("applianceID"));
		event.setAppliancePower(rs.getDouble("appliancePower"));
		event.setTriggerProcedureDeltaPower(rs.getDouble("triggerProcedureDeltaPower"));
		event.setStartTime(rs.getTimestamp("startTime"));
		event.setEndTime(rs.getTimestamp("endTime"));
		event.setTriggerProcedurePowerNumbers(rs.getInt("triggerProcedurePowerNumbers"));
		event.setTriggerProcedurePowerList(rs.getString("triggerProcedurePowerList"));
		return event;
	}
}
