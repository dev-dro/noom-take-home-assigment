package com.noom.interview.fullstack.sleep.entity

import java.time.LocalDate
import java.time.LocalTime

data class SleepLogsAverages(
    val startDate: LocalDate,
    val endDate: LocalDate,
    val averageMinutesSlept: Double,
    val averageStartedSleepAt: LocalTime,
    val averageWokeUpAt: LocalTime,
    val frequencyMorningFeeling: Map<Feeling, Int>,
)