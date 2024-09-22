package com.noom.interview.fullstack.sleep.service

import com.noom.interview.fullstack.sleep.exception.SleepLogNotFoundException
import com.noom.interview.fullstack.sleep.getSleepLog
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
}