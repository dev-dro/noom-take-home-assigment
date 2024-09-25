package com.noom.interview.fullstack.sleep.repository

import com.noom.interview.fullstack.sleep.entity.Feeling
import com.noom.interview.fullstack.sleep.entity.SleepLog
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName
import java.time.LocalDate
import java.time.LocalDateTime

@SpringBootTest
@Testcontainers
class SleepLogJdbcRepositoryIT(
    @Autowired val jdbcTemplate: JdbcTemplate,
    @Autowired val repository: SleepLogJdbcRepository
) {

    @Test
    fun `should return the id when a sleep log is saved`() {
        assertEquals(0, getCount())

        repository.save(getSleepLog())

        assertEquals(1, getCount())
    }

    @Test
    fun `should return the last night log`() {
        val sleepLog = getSleepLog()

        repository.save(sleepLog)

        assertEquals(sleepLog.copy(id = 1), repository.findByUsernameAndDate(sleepLog.username, sleepLog.logDate))
    }

    @Test
    fun `should return null when the last night log is not found`() {
        assertNull(repository.findByUsernameAndDate("john", LocalDate.now()))
    }

    @Test
    fun `should return a list of sleep logs between two dates`() {
        val startDate = LocalDate.now().minusDays(30)
        val endDate = LocalDate.now()
        (0..30).forEach {
            repository.save(getSleepLog(it.toLong()) )
        }
        val result = repository.findByUsernameAndDateBetween("john", startDate, endDate)
        assertEquals(31, result.size)
    }

    @AfterEach
    fun cleanUp() {
        jdbcTemplate.execute("truncate table sleep_log")
        jdbcTemplate.execute("alter sequence sleep_log_id_seq restart with 1")
    }

    private fun getSleepLog(pastDays: Long = 0) = SleepLog(
        id  = null,
        username = "john",
        logDate = LocalDate.now().minusDays(pastDays),
        startedSleepAt = LocalDateTime.now().minusHours(8).minusDays(pastDays),
        wokeUpAt = LocalDateTime.now().minusDays(pastDays),
        morningFeeling = Feeling.OK,
    )

    private fun getCount(): Int {
        return jdbcTemplate.queryForObject("select count(*) from sleep_log", Int::class.java) ?: 0
    }

    companion object {
        @Container
        val container = PostgreSQLContainer<Nothing>(DockerImageName.parse("postgres:13-alpine"))
            .apply {
                withDatabaseName("postgres")
                withUsername("user")
                withPassword("password")
                withInitScript("sql/schema.sql")
            }

        @JvmStatic
        @DynamicPropertySource
        fun datasourceConfig(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", container::getJdbcUrl)
            registry.add("spring.datasource.username", container::getUsername)
            registry.add("spring.datasource.password", container::getPassword)
        }
    }
}