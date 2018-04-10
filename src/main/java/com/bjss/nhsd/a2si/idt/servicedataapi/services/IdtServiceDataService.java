package com.bjss.nhsd.a2si.idt.servicedataapi.services;

import com.bjss.nhsd.a2si.idt.IdtServiceData;
import com.bjss.nhsd.a2si.idt.servicedataapi.domain.AcceptedForLocationCCGByTimePeriod;
import com.bjss.nhsd.a2si.idt.servicedataapi.domain.AcceptedForServiceIdByTimePeriod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class IdtServiceDataService {

    private static final Logger logger = LoggerFactory.getLogger(IdtServiceDataService.class);

    @Autowired
    private DataSource dataSource;

    public void createIdtSystemCaseInfo(List<IdtServiceData> idtServiceDataList) {

        String insert = "INSERT INTO idt_service_data " +
                "(id, call_time, location_ccg, sg_description, " +
                "disposition_code, disposition_text, disposition_group, disposition_broad_group, " +
                "organisation_name, dos_service_id, timestamp) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.batchUpdate(insert, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {

                IdtServiceData idtServiceData = idtServiceDataList.get(i);

                preparedStatement.setString(1, idtServiceData.getId());
                preparedStatement.setString(2, idtServiceData.getCallTime());
                preparedStatement.setString(3, idtServiceData.getLocationCCG());
                preparedStatement.setString(4, idtServiceData.getSgDescription());
                preparedStatement.setString(5, idtServiceData.getDisposition().getCode());
                preparedStatement.setString(6, idtServiceData.getDisposition().getText());
                preparedStatement.setString(7, idtServiceData.getDisposition().getGroup());
                preparedStatement.setString(8, idtServiceData.getDisposition().getBroadGroup());
                preparedStatement.setString(9, idtServiceData.getOrgName());
                preparedStatement.setString(10, idtServiceData.getDosServiceId());
                preparedStatement.setString(11, Timestamp.valueOf(LocalDateTime.now()).toString());
            }

            @Override
            public int getBatchSize() {
                return idtServiceDataList.size();
            }
        });

    }

    public List<AcceptedForServiceIdByTimePeriod> getAcceptedByTimePeriodForServiceId(
            String serviceId, String from, String to) {

        /**
         * SQL Explanation
         *
         * TIME_TO_SEC(call_time) - (TIME_TO_SEC(call_time) MOD 900)
         * This produces the call time in seconds - (the call time in seconds modulus 900)
         * The modulus 900 gives the number of seconds left after dividing into 15 minutes (15 * 60 = 900) blocks
         * Converting back using the sec_to_time function puts it back into a date.
         * So TIME_TO_SEC(call_time) gives the call time time seconds
         *
         *    (TIME_TO_SEC(call_time) MOD 900) gives the number of seconds after the closest and earliest 15 minute block.
         *
         * Subtracting the second from the first gives the time in seconds at that 15 minute block, which is the start time
         * we want so we convert it back into a time and add the date
         *
         * For the to time we do the same but add 899 seconds to the result (899 seconds is 14 minutes and 59 seconds)
         *
         * So a call time of 27th November 2017 10:41:26 will give:
         * a start time of 27th November 10:30:00 and
         * an end date of 27th November 10:44:59
         */

        String query = "SELECT\n" +
                "  dos_service_id as dosServiceId,\n" +
                "  CONCAT( DATE_FORMAT(call_time, '%Y-%m-%d'),\n" +
                "          ' ',\n" +
                "          DATE_FORMAT(SEC_TO_TIME(\n" +
                "                          TIME_TO_SEC(call_time) - (TIME_TO_SEC(call_time) MOD 900)\n" +
                "                      ), '%H:%i:%S')\n" +
                "  ) as FromTime,\n" +
                "  CONCAT( DATE_FORMAT(call_time, '%Y-%m-%d'),\n" +
                "          ' ',\n" +
                "          DATE_FORMAT(SEC_TO_TIME(\n" +
                "                          TIME_TO_SEC(call_time) + 899 - (TIME_TO_SEC(call_time) MOD 900)\n" +
                "                      ), '%H:%i:%S')\n" +
                "  ) as ToTime,\n" +
                "  count(*) as total\n" +
                "FROM idt_service_data\n" +
                "WHERE dos_service_id = ? AND\n" +
                "      call_time BETWEEN TIMESTAMP(?) AND TIMESTAMP(?)\n" +
                "GROUP BY dos_service_id, FromTime, ToTime\n" +
                "ORDER BY dos_service_id, FromTime, ToTime;";

        List<AcceptedForServiceIdByTimePeriod> acceptedForServiceIdByTimePeriodList = new JdbcTemplate(dataSource)
                .query(query, new AcceptedForServiceIdByTimePeriodRowMapper(),
                        serviceId, from, to);

        for (AcceptedForServiceIdByTimePeriod acceptedForServiceIdByTimePeriod : acceptedForServiceIdByTimePeriodList) {
            logger.debug("Accepted For Service Id By Time Period for Service ID {} = {}", serviceId, acceptedForServiceIdByTimePeriod);
        }

        return acceptedForServiceIdByTimePeriodList;

    }

    public List<AcceptedForLocationCCGByTimePeriod> getAcceptedByTimePeriodForLocationCCG(
            String locationCCG, String from, String to) {

        /**
         * SQL Explanation
         *
         * TIME_TO_SEC(call_time) - (TIME_TO_SEC(call_time) MOD 900)
         * This produces the call time in seconds - (the call time in seconds modulus 900)
         * The modulus 900 gives the number of seconds left after dividing into 15 minutes (15 * 60 = 900) blocks
         * Converting back using the sec_to_time function puts it back into a date.
         * So TIME_TO_SEC(call_time) gives the call time time seconds
         *
         *    (TIME_TO_SEC(call_time) MOD 900) gives the number of seconds after the closest and earliest 15 minute block.
         *
         * Subtracting the second from the first gives the time in seconds at that 15 minute block, which is the start time
         * we want so we convert it back into a time and add the date
         *
         * For the to time we do the same but add 899 seconds to the result (899 seconds is 14 minutes and 59 seconds)
         *
         * So a call time of 27th November 2017 10:41:26 will give:
         * a start time of 27th November 10:30:00 and
         * an end date of 27th November 10:44:59
         */

        String query = "SELECT\n" +
                "  location_ccg as location_ccg,\n" +
                "  CONCAT( DATE_FORMAT(call_time, '%Y-%m-%d'),\n" +
                "          ' ',\n" +
                "          DATE_FORMAT(SEC_TO_TIME(\n" +
                "                          TIME_TO_SEC(call_time) - (TIME_TO_SEC(call_time) MOD 900)\n" +
                "                      ), '%H:%i:%S')\n" +
                "  ) as FromTime,\n" +
                "  CONCAT( DATE_FORMAT(call_time, '%Y-%m-%d'),\n" +
                "          ' ',\n" +
                "          DATE_FORMAT(SEC_TO_TIME(\n" +
                "                          TIME_TO_SEC(call_time) + 899 - (TIME_TO_SEC(call_time) MOD 900)\n" +
                "                      ), '%H:%i:%S')\n" +
                "  ) as ToTime,\n" +
                "  count(*) as total\n" +
                "FROM idt_service_data\n" +
                "WHERE location_ccg = ? AND\n" +
                "      call_time BETWEEN TIMESTAMP(?) AND TIMESTAMP(?)\n" +
                "GROUP BY location_ccg, FromTime, ToTime\n" +
                "ORDER BY location_ccg, FromTime, ToTime;";

        List<AcceptedForLocationCCGByTimePeriod> acceptedForLocationCCGByTimePeriodList = new JdbcTemplate(dataSource)
                .query(query, new AcceptedForLocationCCGByTimePeriodRowMapper(),
                        locationCCG, from, to);

        for (AcceptedForLocationCCGByTimePeriod acceptedForLocationCCGByTimePeriod :
                acceptedForLocationCCGByTimePeriodList) {
            logger.debug("Accepted For Service Id By Time Period for location CCG {} = {}", locationCCG, acceptedForLocationCCGByTimePeriod);
        }

        return acceptedForLocationCCGByTimePeriodList;

    }
}
