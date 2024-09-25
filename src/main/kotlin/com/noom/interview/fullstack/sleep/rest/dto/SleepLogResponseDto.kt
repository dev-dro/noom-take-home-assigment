package com.noom.interview.fullstack.sleep.rest.dto

data class SleepLogResponseDto(
    val logDate: String,
    val startedSleepAt: String,
    val wokeUpAt: String,
    val minutesSlept: Long,
    val morningFeeling: String
)