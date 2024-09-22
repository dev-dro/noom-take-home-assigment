package com.noom.interview.fullstack.sleep.repository

import com.noom.interview.fullstack.sleep.entity.SleepLog
import java.time.LocalDate

interface SleepLogRepository {
    fun save(sleepLog: SleepLog): Long
    fun findByUsernameAndDate(username: String, date: LocalDate): SleepLog?
}