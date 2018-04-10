package com.bjss.nhsd.a2si.idt.servicedataapi.services;

import com.bjss.nhsd.a2si.idt.servicedataapi.domain.AcceptedForLocationCCGByTimePeriod;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AcceptedForLocationCCGByTimePeriodRowMapper implements RowMapper{
    @Override
    public AcceptedForLocationCCGByTimePeriod mapRow(ResultSet resultSet, int i) throws SQLException {
        return new AcceptedForLocationCCGByTimePeriod(
                resultSet.getString("location_ccg"),
                resultSet.getString("fromTime"),
                resultSet.getString("toTime"),
                resultSet.getInt("total")
        );
    }
}
