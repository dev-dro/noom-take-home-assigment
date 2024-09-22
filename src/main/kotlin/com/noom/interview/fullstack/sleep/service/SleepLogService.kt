package com.noom.interview.fullstack.sleep.service

import com.noom.interview.fullstack.sleep.entity.SleepLog
import com.noom.interview.fullstack.sleep.exception.SleepLogNotFoundException
import com.noom.interview.fullstack.sleep.repository.SleepLogRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class SleepLogService(
    private val sleepLogRepository: SleepLogRepository
) {

    fun createSleepLog(sleepLog: SleepLog): Long {
        return sleepLogRepository.save(sleepLog)
    }

    fun findLastNightSleepLog(username: String): SleepLog {
        return sleepLogRepository.findByUsernameAndDate(username, LocalDate.now()) ?: throw SleepLogNotFoundException()
    }
}