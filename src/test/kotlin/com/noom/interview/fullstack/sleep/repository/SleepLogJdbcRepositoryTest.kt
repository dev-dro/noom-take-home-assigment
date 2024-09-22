package com.noom.interview.fullstack.sleep.repository

import com.noom.interview.fullstack.sleep.getSleepLog
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

@ExtendWith(MockKExtension::class)
class SleepLogJdbcRepositoryTest {

    @MockK
    lateinit var jdbcTemplate: NamedParameterJdbcTemplate

    @InjectMockKs
    lateinit var repository: SleepLogJdbcRepository

    @Test
    fun `should return the id when a sleep log is saved`() {
        every { jdbcTemplate.update(any<String>(), any<MapSqlParameterSource>()) } returns 1

        assertEquals(1L, repository.save(getSleepLog()))
    }
}