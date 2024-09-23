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
        startedSleep = dto.startedSleep ?: throw FieldValueInvalidException("startedSleep"),
        wokeUp = dto.wokeUp ?: throw FieldValueInvalidException("wokeUp"),
        date = dto.wokeUp.toLocalDate(),
        minutesSlept = Duration.between(dto.startedSleep, dto.wokeUp).toMinutes(),
        feltWhenWokeUp = try { Feeling.valueOf(dto.feltWhenWokeUp ?: "") }
            catch (e: IllegalArgumentException) { throw FieldValueInvalidException("wokeUp") }
    )

fun sleepLogToSleepLogResponseDto(sleepLog: SleepLog) =
    SleepLogResponseDto(
        date = sleepLog.date.format(DateTimeFormatter.ofPattern(DATE_FORMAT)),
        startedSleep = sleepLog.startedSleep.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)),
        wokeUp = sleepLog.wokeUp.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)),
        minutesSlept = sleepLog.minutesSlept,
        feltWhenWokeUp = sleepLog.feltWhenWokeUp.name
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