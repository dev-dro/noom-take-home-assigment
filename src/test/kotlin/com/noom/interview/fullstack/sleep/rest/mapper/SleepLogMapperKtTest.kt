package com.noom.interview.fullstack.sleep.rest.mapper

import com.noom.interview.fullstack.sleep.getSleepLog
import com.noom.interview.fullstack.sleep.getSleepLogsAverages
import com.noom.interview.fullstack.sleep.rest.dto.CreateSleepLogRequestDto
import com.noom.interview.fullstack.sleep.rest.dto.SleepLogResponseDto
import com.noom.interview.fullstack.sleep.rest.dto.SleepLogsAveragesResponseDto
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate

class SleepLogMapperKtTest {

    @Test
    fun `should return a SleepLog object from the CreateSleepLogRequestDto object`() {
        val username = "john"
        val dto = CreateSleepLogRequestDto(
            startedSleepAt = "2024-09-21T23:00:00.000Z",
            wokeUpAt = "2024-09-22T07:00:00.000Z",
            morningFeeling = "GOOD",
        )

        val result = createSleepLogRequestDtoToSleepLog(dto, username)
        assertEquals(getSleepLog(), result)
    }

    @Test
    fun `should return a SleepLogResponseDto object from the SleepLog object`() {
        val dto = SleepLogResponseDto(
            logDate = "2024-09-22",
            startedSleepAt = "2024-09-21T23:00:00.000Z",
            wokeUpAt = "2024-09-22T07:00:00.000Z",
            minutesSlept = 480,
            morningFeeling = "GOOD",
        )

        val result = sleepLogToSleepLogResponseDto(getSleepLog())
        assertEquals(dto, result)
    }

    @Test
    fun `should return a SleepLogsAveragesDto object from the SleepLogsAverages object`() {
        val averages = getSleepLogsAverages(startDate = LocalDate.of(2024, 8, 23), endDate = LocalDate.of(2024, 9, 22))
        val dto = SleepLogsAveragesResponseDto(
            startDate = "2024-08-23",
            endDate = "2024-09-22",
            averageMinutesSlept = 500.0,
            averageStartedSleepAt = "22:50",
            averageWokeUpAt = "07:10",
            frequencyMorningFeeling = mapOf("GOOD" to 10, "BAD" to 10, "OK" to 10),
        )

        val result = sleepLogsAveragesToSleepLogsAveragesDto(averages)
        assertEquals(dto, result)
    }
}