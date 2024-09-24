package com.noom.interview.fullstack.sleep.repository

import com.noom.interview.fullstack.sleep.entity.Feeling
import com.noom.interview.fullstack.sleep.entity.SleepLog
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

class SleepLogRowMapper: RowMapper<SleepLog> {
    override fun mapRow(rs: ResultSet, rowNum: Int): SleepLog =
        SleepLog(
            id = rs.getLong("id"),
            username = rs.getString("username"),
            logDate = rs.getDate("log_date").toLocalDate(),
            startedSleepAt = rs.getTimestamp("started_sleep_at").toLocalDateTime(),
            wokeUpAt = rs.getTimestamp("woke_up_at").toLocalDateTime(),
            morningFeeling = Feeling.valueOf(rs.getString("morning_feeling")),
        )
}