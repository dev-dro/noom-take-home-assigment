package com.noom.interview.fullstack.sleep.entity

import java.time.LocalDate
import java.time.LocalTime

data class SleepLogsAverages(
    val startDate: LocalDate,
    val endDate: LocalDate,
    val averageMinutesSlept: Double,
    val averageStartedSleep: LocalTime,
    val averageWokeUp: LocalTime,
    val frequencyFeltWhenWokeUp: Map<Feeling, Int>,
)