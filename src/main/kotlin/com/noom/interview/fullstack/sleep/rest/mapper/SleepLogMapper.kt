package com.noom.interview.fullstack.sleep.rest.mapper

import com.noom.interview.fullstack.sleep.entity.Feeling
import com.noom.interview.fullstack.sleep.entity.SleepLog
import com.noom.interview.fullstack.sleep.entity.SleepLogsAverages
import com.noom.interview.fullstack.sleep.exception.FieldValueInvalidException
import com.noom.interview.fullstack.sleep.rest.dto.CreateSleepLogRequestDto
import com.noom.interview.fullstack.sleep.rest.dto.SleepLogResponseDto
import com.noom.interview.fullstack.sleep.rest.dto.SleepLogsAveragesResponseDto
import java.time.*
import java.time.format.DateTimeFormatter

const val DATE_FORMAT = "yyyy-MM-dd"
const val TIME_FORMAT = "HH:mm"
const val DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

fun createSleepLogRequestDtoToSleepLog(dto: CreateSleepLogRequestDto, username: String): SleepLog =
    SleepLog(
        id = null,
        username = username,
        startedSleepAt = dto.startedSleepAt?.toLocalDateTime() ?: throw FieldValueInvalidException("startedSleep"),
        wokeUpAt = dto.wokeUpAt?.toLocalDateTime() ?: throw FieldValueInvalidException("wokeUp"),
        logDate = dto.wokeUpAt.toLocalDate(),
        morningFeeling = try { Feeling.valueOf(dto.morningFeeling ?: "") }
            catch (e: IllegalArgumentException) { throw FieldValueInvalidException("wokeUp") }
    )

fun sleepLogToSleepLogResponseDto(sleepLog: SleepLog) =
    SleepLogResponseDto(
        logDate = sleepLog.logDate.format(DateTimeFormatter.ofPattern(DATE_FORMAT)),
        startedSleepAt = sleepLog.startedSleepAt.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)),
        wokeUpAt = sleepLog.wokeUpAt.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)),
        minutesSlept = Duration.between(sleepLog.startedSleepAt, sleepLog.wokeUpAt).toMinutes(),
        morningFeeling = sleepLog.morningFeeling.name
    )

fun sleepLogsAveragesToSleepLogsAveragesDto(sleepLogsAverages: SleepLogsAverages): SleepLogsAveragesResponseDto =
    SleepLogsAveragesResponseDto(
        startDate = sleepLogsAverages.startDate.format(DateTimeFormatter.ofPattern(DATE_FORMAT)),
        endDate = sleepLogsAverages.endDate.format(DateTimeFormatter.ofPattern(DATE_FORMAT)),
        averageMinutesSlept = sleepLogsAverages.averageMinutesSlept,
        averageStartedSleepAt = sleepLogsAverages.averageStartedSleepAt.format(DateTimeFormatter.ofPattern(TIME_FORMAT)),
        averageWokeUpAt = sleepLogsAverages.averageWokeUpAt.format(DateTimeFormatter.ofPattern(TIME_FORMAT)),
        frequencyMorningFeeling = sleepLogsAverages.frequencyMorningFeeling.mapKeys { it.key.name }
    )

private fun String.toLocalDateTime(): LocalDateTime =
    LocalDateTime.ofInstant(Instant.parse(this), ZoneOffset.UTC)

private fun String.toLocalDate(): LocalDate =
    LocalDate.ofInstant(Instant.parse(this), ZoneOffset.UTC)
