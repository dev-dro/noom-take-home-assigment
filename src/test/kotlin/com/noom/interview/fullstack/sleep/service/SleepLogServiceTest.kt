package com.noom.interview.fullstack.sleep.service

import com.noom.interview.fullstack.sleep.getSleepLog
import com.noom.interview.fullstack.sleep.repository.SleepLogRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

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
}