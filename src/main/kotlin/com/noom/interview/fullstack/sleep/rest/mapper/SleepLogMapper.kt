package com.noom.interview.fullstack.sleep.rest.mapper

import com.noom.interview.fullstack.sleep.entity.Feeling
import com.noom.interview.fullstack.sleep.entity.SleepLog
import com.noom.interview.fullstack.sleep.entity.SleepLogsAverages
import com.noom.interview.fullstack.sleep.exception.FieldValueInvalidException
import com.noom.interview.fullstack.sleep.rest.dto.CreateSleepLogRequestDto
import com.noom.interview.fullstack.sleep.rest.dto.SleepLogResponseDto
import com.noom.interview.fullstack.sleep.rest.dto.SleepLogsAveragesDto
import java.time.Duration
import java.time.format.DateTimeFormatter

const val DATE_FORMAT = "yyyy-MM-dd"
const val TIME_FORMAT = "HH:mm"
const val DATE_TIME_FORMAT = "$DATE_FORMAT $TIME_FORMAT"

fun createSleepLogRequestDtoToSleepLog(dto: CreateSleepLogRequestDto, username: String): SleepLog =
    SleepLog(
        id = null,
        username = username,
        startedSleepAt = dto.startedSleep ?: throw FieldValueInvalidException("startedSleep"),
        wokeUpAt = dto.wokeUp ?: throw FieldValueInvalidException("wokeUp"),
        logDate = dto.wokeUp.toLocalDate(),
        morningFeeling = try { Feeling.valueOf(dto.feltWhenWokeUp ?: "") }
            catch (e: IllegalArgumentException) { throw FieldValueInvalidException("wokeUp") }
    )

fun sleepLogToSleepLogResponseDto(sleepLog: SleepLog) =
    SleepLogResponseDto(
        date = sleepLog.logDate.format(DateTimeFormatter.ofPattern(DATE_FORMAT)),
        startedSleep = sleepLog.startedSleepAt.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)),
        wokeUp = sleepLog.wokeUpAt.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)),
        minutesSlept = Duration.between(sleepLog.startedSleepAt, sleepLog.startedSleepAt).toMinutes(),
        feltWhenWokeUp = sleepLog.morningFeeling.name
    )

fun sleepLogsAveragesToSleepLogsAveragesDto(sleepLogsAverages: SleepLogsAverages): SleepLogsAveragesDto =
    SleepLogsAveragesDto(
        startDate = sleepLogsAverages.startDate.format(DateTimeFormatter.ofPattern(DATE_FORMAT)),
        endDate = sleepLogsAverages.endDate.format(DateTimeFormatter.ofPattern(DATE_FORMAT)),
        averageMinutesSlept = sleepLogsAverages.averageMinutesSlept,
        averageStartedSleep = sleepLogsAverages.averageStartedSleep.format(DateTimeFormatter.ofPattern(TIME_FORMAT)),
        averageWokeUp = sleepLogsAverages.averageWokeUp.format(DateTimeFormatter.ofPattern(TIME_FORMAT)),
        frequencyFeltWhenWokeUp = sleepLogsAverages.frequencyFeltWhenWokeUp.mapKeys { it.key.name }
    )