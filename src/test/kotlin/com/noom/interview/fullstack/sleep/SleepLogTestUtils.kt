package com.noom.interview.fullstack.sleep

import com.noom.interview.fullstack.sleep.entity.Feeling
import com.noom.interview.fullstack.sleep.entity.SleepLog
import com.noom.interview.fullstack.sleep.entity.SleepLogsAverages
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

fun getSleepLog(id: Long? = null, variation: Int = 0, feeling: Feeling = Feeling.GOOD): SleepLog =
    SleepLog(
        id  = id,
        username = "john",
        logDate = LocalDate.of(2024, 9, 22).minusDays(id ?: 0),
        startedSleepAt = LocalDateTime.of(2024, 9, 21, 23, 0).minusDays(id ?: 0).minusMinutes(variation * 10L),
        wokeUpAt = LocalDateTime.of(2024, 9, 22, 7, 0).minusDays(id ?: 0).plusMinutes(variation * 10L),
        morningFeeling = feeling,
    )

fun getSleepLogsAverages(
    startDate: LocalDate = LocalDate.now().minusDays(30),
    endDate: LocalDate = LocalDate.now(),
    total: Int = 30,
): SleepLogsAverages {
    val frequency: Int = total / 3
    return SleepLogsAverages(
        startDate = startDate,
        endDate = endDate,
        averageMinutesSlept = 500.0,
        averageStartedSleepAt = LocalTime.of(22, 50),
        averageWokeUpAt = LocalTime.of(7, 10),
        frequencyMorningFeeling = mapOf(Feeling.GOOD to frequency, Feeling.OK to frequency, Feeling.BAD to frequency)
    )
}