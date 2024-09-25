package com.noom.interview.fullstack.sleep.rest.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime
import javax.validation.constraints.NotNull

data class CreateSleepLogRequestDto(
    @field:NotNull(message = "The field startedSleepAt is required")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    val startedSleepAt: LocalDateTime?,

    @field:NotNull(message = "The field wokeUpAt is required")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    val wokeUpAt: LocalDateTime?,

    @field:NotNull(message = "The field morningFeeling is required")
    val morningFeeling: String?,
)