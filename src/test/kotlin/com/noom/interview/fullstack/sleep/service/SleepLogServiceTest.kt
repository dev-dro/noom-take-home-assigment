package com.noom.interview.fullstack.sleep.service

import com.noom.interview.fullstack.sleep.entity.Feeling
import com.noom.interview.fullstack.sleep.exception.SleepLogNotFoundException
import com.noom.interview.fullstack.sleep.getSleepLog
import com.noom.interview.fullstack.sleep.getSleepLogsAverages
import com.noom.interview.fullstack.sleep.repository.SleepLogRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDate

@ExtendWith(MockKExtension::class)
class SleepLogServiceTest {

    @MockK
    lateinit var sleepLogRepository: SleepLogRepository

    @InjectMockKs
    lateinit var sleepLogService: SleepLogService

    @Test
    fun `should return the saved id when the sleep log is saved`() {
        val sleepLog = getSleepLog()

        every { sleepLogRepository.save(sleepLog) } returns 1L

        assertEquals(1L, sleepLogService.createSleepLog(sleepLog))
    }

    @Test
    fun `should return the last night log`() {
        val sleepLog = getSleepLog()
        val username = "john"

        every { sleepLogRepository.findByUsernameAndDate(username, LocalDate.now()) } returns sleepLog

        assertEquals(sleepLog, sleepLogService.findLastNightSleepLog(username))
    }

    @Test
    fun `should throw error when the last night log is not found`() {
        val username = "john"

        every { sleepLogRepository.findByUsernameAndDate(username, LocalDate.now()) } returns null

        assertThrows(SleepLogNotFoundException::class.java) { sleepLogService.findLastNightSleepLog(username) }
    }

    @Test
    fun `should return the sleep logs averages of the last 30 days`() {
        val username = "john"
        val sleepLogs = (1..10).map { getSleepLog(it.toLong()) } +
                (11..20).map { getSleepLog(it.toLong(), 1, Feeling.BAD) } +
                (21..30).map { getSleepLog(it.toLong(), 2, Feeling.OK) }
        val averages = getSleepLogsAverages()

        every { sleepLogRepository.findByUsernameAndDateBetween(username, any(), any()) } returns sleepLogs

        val result = sleepLogService.findSleepLogsAveragesFrom30Days(username)

        assertEquals(averages, result)
    }

    @Test
    fun `should return the sleep logs averages when there's less than 30 logs`() {
        val username = "john"
        val sleepLogs = (1..5).map { getSleepLog(it.toLong()) } +
                (6..10).map { getSleepLog(it.toLong(), 1, Feeling.BAD) } +
                (11..15).map { getSleepLog(it.toLong(), 2, Feeling.OK) }
        val averages = getSleepLogsAverages(total = 15)

        every { sleepLogRepository.findByUsernameAndDateBetween(username, any(), any()) } returns sleepLogs

        val result = sleepLogService.findSleepLogsAveragesFrom30Days(username)

        assertEquals(averages, result)
    }

    @Test
    fun `should throw error when there's no sleep log in 30 days`() {
        val username = "john"

        every { sleepLogRepository.findByUsernameAndDateBetween(username, any(), any()) } returns emptyList()

        assertThrows(SleepLogNotFoundException::class.java) { sleepLogService.findSleepLogsAveragesFrom30Days(username) }
    }
}