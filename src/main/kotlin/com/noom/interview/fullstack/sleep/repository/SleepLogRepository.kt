package com.noom.interview.fullstack.sleep.repository

import com.noom.interview.fullstack.sleep.entity.SleepLog

interface SleepLogRepository {
    fun save(sleepLog: SleepLog): Long
}