package com.noom.interview.fullstack.sleep

import com.noom.interview.fullstack.sleep.entity.Feeling
import com.noom.interview.fullstack.sleep.entity.SleepLog
import java.time.LocalDate
import java.time.LocalDateTime

fun getSleepLog(id: Long? = null): SleepLog = SleepLog(
    id  = id,
    username = "john",
    date = LocalDate.of(2024, 9, 22),
    startedSleep = LocalDateTime.of(2024, 9, 21, 23, 0),
    wokeUp = LocalDateTime.of(2024, 9, 22, 7, 0),
    minutesSlept = 480,
    feltWhenWokeUp = Feeling.GOOD,
)