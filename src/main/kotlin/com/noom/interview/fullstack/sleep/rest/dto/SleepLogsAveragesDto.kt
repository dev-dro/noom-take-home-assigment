package com.noom.interview.fullstack.sleep.rest.dto

data class SleepLogsAveragesDto(
    val startDate: String,
    val endDate: String,
    val averageMinutesSlept: Double,
    val averageStartedSleep: String,
    val averageWokeUp: String,
    val frequencyFeltWhenWokeUp: Map<String, Int>,
)