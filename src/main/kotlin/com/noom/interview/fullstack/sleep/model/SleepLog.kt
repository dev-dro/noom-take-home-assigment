package com.noom.interview.fullstack.sleep.model

import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime

data class SleepLog(
    private val id: Long,
    private val username: String,
    private val date: LocalDate,
    private val startTime: LocalDateTime,
    private val endTime: LocalDateTime,
    private val totalTime: Duration,
    private val felling: Feeling,
)

enum class Feeling {
    BAD, OK, GOOD
}
