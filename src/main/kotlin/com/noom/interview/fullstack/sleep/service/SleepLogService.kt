package com.noom.interview.fullstack.sleep.service

import com.noom.interview.fullstack.sleep.entity.SleepLog
import com.noom.interview.fullstack.sleep.entity.SleepLogsAverages
import com.noom.interview.fullstack.sleep.exception.SleepLogAlreadyExistsException
import com.noom.interview.fullstack.sleep.exception.SleepLogInvalidTimeException
import com.noom.interview.fullstack.sleep.exception.SleepLogNotFoundException
import com.noom.interview.fullstack.sleep.repository.SleepLogRepository
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Service
class SleepLogService(
    private val sleepLogRepository: SleepLogRepository
) {

    fun createSleepLog(sleepLog: SleepLog) {
        sleepLogRepository.findByUsernameAndDate(sleepLog.username, sleepLog.logDate)?.let {
            throw SleepLogAlreadyExistsException()
        }

        if (!sleepLog.startedSleepAt.isBefore(sleepLog.wokeUpAt)) {
            throw SleepLogInvalidTimeException("The startSleepAt needs to be before the wokeUpAt")
        }

        if (sleepLog.wokeUpAt.isAfter(LocalDateTime.now())) {
            throw SleepLogInvalidTimeException("The wokeUpAt needs to be before the current time")
        }

        sleepLogRepository.save(sleepLog)
    }

    fun findLastNightSleepLog(username: String): SleepLog {
        return sleepLogRepository.findByUsernameAndDate(username, LocalDate.now()) ?: throw SleepLogNotFoundException()
    }

    fun findSleepLogsAveragesFrom30Days(username: String): SleepLogsAverages {
        val startDate = LocalDate.now().minusDays(30)
        val endDate = LocalDate.now()
        val sleepLogs = sleepLogRepository.findByUsernameAndDateBetween(username, startDate, endDate)

        if (sleepLogs.isEmpty()) throw SleepLogNotFoundException()

        return SleepLogsAverages(
            startDate = startDate,
            endDate = endDate,
            averageMinutesSlept = sleepLogs.map { Duration.between(it.startedSleepAt, it.wokeUpAt).toMinutes() }.average(),
            averageStartedSleepAt = sleepLogs.map { it.startedSleepAt.toLocalTime() }.average(),
            averageWokeUpAt = sleepLogs.map { it.wokeUpAt.toLocalTime() }.average(),
            frequencyMorningFeeling = sleepLogs.groupBy { it.morningFeeling }.mapValues { it.value.size }
        )
    }

    private fun List<LocalTime>.average(): LocalTime =
        this.map { it.toNanoOfDay() }.average()
            .toLong().let { LocalTime.ofNanoOfDay(it) }
}