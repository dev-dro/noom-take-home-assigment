package com.noom.interview.fullstack.sleep.rest.dto

data class SleepLogResponseDto(
    val date: String,
    val startedSleep: String,
    val wokeUp: String,
    val minutesSlept: Long,
    val feltWhenWokeUp: String
)