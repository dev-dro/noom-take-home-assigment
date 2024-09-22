package com.noom.interview.fullstack.sleep.entity

import java.time.LocalDate
import java.time.LocalDateTime

data class SleepLog(
    val id: Long?,
    val username: String,
    val date: LocalDate,
    val startedSleep: LocalDateTime,
    val wokeUp: LocalDateTime,
    val minutesSlept: Long,
    val feltWhenWokeUp: Feeling,
)

enum class Feeling {
    BAD, OK, GOOD
}
