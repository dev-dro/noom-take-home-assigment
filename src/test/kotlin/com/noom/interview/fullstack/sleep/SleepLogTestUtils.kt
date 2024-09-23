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
        date = LocalDate.of(2024, 9, 22).minusDays(id ?: 0),
        startedSleep = LocalDateTime.of(2024, 9, 21, 23, 0).minusDays(id ?: 0).minusMinutes(variation * 10L),
        wokeUp = LocalDateTime.of(2024, 9, 22, 7, 0).minusDays(id ?: 0).minusMinutes(variation * 10L),
        minutesSlept = 480 - (variation * 10L),
        feltWhenWokeUp = feeling,
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
        averageMinutesSlept = 470.0,
        averageStartedSleep = LocalTime.of(22, 50),
        averageWokeUp = LocalTime.of(6, 50),
        frequencyFeltWhenWokeUp = mapOf(Feeling.GOOD to frequency, Feeling.OK to frequency, Feeling.BAD to frequency)
    )
}