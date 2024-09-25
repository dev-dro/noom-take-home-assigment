package com.noom.interview.fullstack.sleep.rest.dto

data class SleepLogsAveragesResponseDto(
    val startDate: String,
    val endDate: String,
    val averageMinutesSlept: Double,
    val averageStartedSleepAt: String,
    val averageWokeUpAt: String,
    val frequencyMorningFeeling: Map<String, Int>,
)