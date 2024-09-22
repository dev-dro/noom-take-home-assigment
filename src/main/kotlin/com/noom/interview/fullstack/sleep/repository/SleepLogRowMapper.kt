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
            date = rs.getDate("date").toLocalDate(),
            startedSleep = rs.getTimestamp("started_sleep").toLocalDateTime(),
            wokeUp = rs.getTimestamp("woke_up").toLocalDateTime(),
            minutesSlept = rs.getLong("minutes_slept"),
            feltWhenWokeUp = Feeling.valueOf(rs.getString("felt_when_woke_up")),
        )
}