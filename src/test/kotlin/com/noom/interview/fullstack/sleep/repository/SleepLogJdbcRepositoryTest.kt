package com.noom.interview.fullstack.sleep.repository

import com.noom.interview.fullstack.sleep.getSleepLog
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import java.time.LocalDate

@ExtendWith(MockKExtension::class)
class SleepLogJdbcRepositoryTest {

    @MockK
    lateinit var jdbcTemplate: NamedParameterJdbcTemplate

    @InjectMockKs
    lateinit var repository: SleepLogJdbcRepository

    @Test
    fun `should return the id when a sleep log is saved`() {
        every { jdbcTemplate.update(any<String>(), any<MapSqlParameterSource>()) } returns 1

        repository.save(getSleepLog())
        verify { jdbcTemplate.update(any<String>(), any<MapSqlParameterSource>()) }
    }

    @Test
    fun `should return the last night log`() {
        val sleepLog = getSleepLog()

        every { jdbcTemplate.queryForObject(any<String>(), any<MapSqlParameterSource>(), any<SleepLogRowMapper>()) } returns sleepLog

        assertEquals(sleepLog, repository.findByUsernameAndDate(sleepLog.username, sleepLog.date))
    }

    @Test
    fun `should return null when the last night log is not found`() {
        every { jdbcTemplate.queryForObject(any<String>(), any<MapSqlParameterSource>(), any<SleepLogRowMapper>()) } throws EmptyResultDataAccessException(1)

        assertNull(repository.findByUsernameAndDate("john", LocalDate.now()))
    }

    @Test
    fun `should return a list of sleep logs between two dates`() {
        val startDate = LocalDate.now().minusDays(30)
        val endDate = LocalDate.now()
        val sleepLogs = (1..30).map { getSleepLog(it.toLong()) }

        every { jdbcTemplate.query(any<String>(), any<MapSqlParameterSource>(), any<SleepLogRowMapper>()) } returns sleepLogs

        assertEquals(sleepLogs, repository.findByUsernameAndDateBetween("john", startDate, endDate))
    }
}