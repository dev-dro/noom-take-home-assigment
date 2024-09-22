package com.noom.interview.fullstack.sleep.repository

import com.noom.interview.fullstack.sleep.entity.SleepLog
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class SleepLogJdbcRepository(
    private val jdbcTemplate: NamedParameterJdbcTemplate,
): SleepLogRepository {

    override fun save(sleepLog: SleepLog): Long =
        jdbcTemplate.update(
            "insert into sleep_log(username, date, started_sleep, woke_up, minutes_slept, felt_when_woke_up)  " +
                    "values (:username, :date, :startedSleep, :wokeUp, :minutesSlept, :feltWhenWokeUp)",
            MapSqlParameterSource()
                .addValue("username", sleepLog.username)
                .addValue("date", sleepLog.date)
                .addValue("startedSleep", sleepLog.startedSleep)
                .addValue("wokeUp", sleepLog.wokeUp)
                .addValue("minutesSlept", sleepLog.minutesSlept)
                .addValue("feltWhenWokeUp", sleepLog.feltWhenWokeUp.toString()),
        ).toLong()

}