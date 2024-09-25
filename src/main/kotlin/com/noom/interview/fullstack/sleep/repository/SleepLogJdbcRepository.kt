package com.noom.interview.fullstack.sleep.repository

import com.noom.interview.fullstack.sleep.entity.SleepLog
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class SleepLogJdbcRepository(
    private val jdbcTemplate: NamedParameterJdbcTemplate,
): SleepLogRepository {

    override fun save(sleepLog: SleepLog) {
        jdbcTemplate.update(
            "insert into sleep_log(username, log_date, started_sleep_at, woke_up_at, morning_feeling)  " +
                    "values (:username, :logDate, :startedSleepAt, :wokeUpAt, :morningFeeling::feeling)",
            MapSqlParameterSource()
                .addValue("username", sleepLog.username)
                .addValue("logDate", sleepLog.logDate)
                .addValue("startedSleepAt", sleepLog.startedSleepAt)
                .addValue("wokeUpAt", sleepLog.wokeUpAt)
                .addValue("morningFeeling", sleepLog.morningFeeling.toString()),
        )
    }

    override fun findByUsernameAndDate(username: String, logDate: LocalDate): SleepLog? =
        try {
            jdbcTemplate.queryForObject(
                "select * from sleep_log where username = :username and log_date = :logDate limit 1",
                MapSqlParameterSource()
                    .addValue("username", username)
                    .addValue("logDate", logDate),
                SleepLogRowMapper(),
            )
        } catch (e: EmptyResultDataAccessException) { null }

    override fun findByUsernameAndDateBetween(
        username: String,
        startDate: LocalDate,
        endDate: LocalDate
    ): List<SleepLog> =
        jdbcTemplate.query(
            "select * from sleep_log where username = :username and log_date between :startDate and :endDate",
            MapSqlParameterSource()
                .addValue("username", username)
                .addValue("startDate", startDate)
                .addValue("endDate", endDate),
            SleepLogRowMapper(),
        )

}