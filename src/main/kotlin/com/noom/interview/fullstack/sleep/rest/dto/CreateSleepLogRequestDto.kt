package com.noom.interview.fullstack.sleep.rest.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime
import javax.validation.constraints.NotNull

data class CreateSleepLogRequestDto(
    @field:NotNull(message = "The field startedSleep is required")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val startedSleep: LocalDateTime?,

    @field:NotNull(message = "The field wokeUp is required")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val wokeUp: LocalDateTime?,

    @field:NotNull(message = "The field feltWhenWokeUp is required")
    val feltWhenWokeUp: String?,
)