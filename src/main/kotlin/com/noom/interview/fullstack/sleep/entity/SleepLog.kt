package com.noom.interview.fullstack.sleep.entity

import java.time.LocalDate
import java.time.LocalDateTime

data class SleepLog(
    val id: Long?,
    val username: String,
    val logDate: LocalDate,
    val startedSleepAt: LocalDateTime,
    val wokeUpAt: LocalDateTime,
    val morningFeeling: Feeling,
)

enum class Feeling {
    BAD, OK, GOOD
}
