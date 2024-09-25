package com.noom.interview.fullstack.sleep.service

import com.noom.interview.fullstack.sleep.entity.SleepLog
import com.noom.interview.fullstack.sleep.entity.SleepLogsAverages
import com.noom.interview.fullstack.sleep.exception.SleepLogNotFoundException
import com.noom.interview.fullstack.sleep.repository.SleepLogRepository
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime

@Service
class SleepLogService(
    private val sleepLogRepository: SleepLogRepository
) {

    fun createSleepLog(sleepLog: SleepLog) {
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
            averageStartedSleep = sleepLogs.map { it.startedSleepAt.toLocalTime() }.average(),
            averageWokeUp = sleepLogs.map { it.wokeUpAt.toLocalTime() }.average(),
            frequencyFeltWhenWokeUp = sleepLogs.groupBy { it.morningFeeling }.mapValues { it.value.size }
        )
    }

    private fun List<LocalTime>.average(): LocalTime =
        this.map { it.toNanoOfDay() }.average()
            .toLong().let { LocalTime.ofNanoOfDay(it) }
}