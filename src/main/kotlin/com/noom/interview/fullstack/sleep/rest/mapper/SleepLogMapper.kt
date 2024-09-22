package com.noom.interview.fullstack.sleep.rest.mapper

import com.noom.interview.fullstack.sleep.entity.Feeling
import com.noom.interview.fullstack.sleep.entity.SleepLog
import com.noom.interview.fullstack.sleep.exception.FieldValueInvalidException
import com.noom.interview.fullstack.sleep.rest.dto.CreateSleepLogRequestDto
import java.time.Duration

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