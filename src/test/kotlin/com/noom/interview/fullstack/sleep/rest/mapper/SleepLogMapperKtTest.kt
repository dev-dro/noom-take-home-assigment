package com.noom.interview.fullstack.sleep.rest.mapper

import com.noom.interview.fullstack.sleep.getSleepLog
import com.noom.interview.fullstack.sleep.rest.dto.CreateSleepLogRequestDto
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class SleepLogMapperKtTest {

    @Test
    fun `should return a SleepLog object from the CreateSleepLogRequestDto object`() {
        val username = "john"
        val dto = CreateSleepLogRequestDto(
            startedSleep = LocalDateTime.of(2024, 9, 21, 23, 0),
            wokeUp = LocalDateTime.of(2024, 9, 22, 7, 0),
            feltWhenWokeUp = "GOOD",
        )

        val result = createSleepLogRequestDtoToSleepLog(dto, username)
        assertEquals(getSleepLog(), result)
    }
}