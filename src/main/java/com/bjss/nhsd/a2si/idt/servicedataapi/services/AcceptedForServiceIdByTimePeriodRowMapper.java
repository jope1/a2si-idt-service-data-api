package com.bjss.nhsd.a2si.idt.servicedataapi.services;

import com.bjss.nhsd.a2si.idt.servicedataapi.domain.AcceptedForServiceIdByTimePeriod;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AcceptedForServiceIdByTimePeriodRowMapper implements RowMapper{
    @Override
    public AcceptedForServiceIdByTimePeriod mapRow(ResultSet resultSet, int i) throws SQLException {
        return new AcceptedForServiceIdByTimePeriod(
                resultSet.getString("dosServiceId"),
                resultSet.getString("fromTime"),
                resultSet.getString("toTime"),
                resultSet.getInt("total")
        );
    }
}
