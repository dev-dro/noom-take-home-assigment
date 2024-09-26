package com.noom.interview.fullstack.sleep.rest.dto

import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

data class CreateSleepLogRequestDto(

    @Schema(description = "The date and time when the user started to sleep", example = "2024-09-21T23:00:00.000Z")
    @field:NotNull(message = "The field startedSleepAt is required")
    @field:Pattern(regexp = "^[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}.[0-9]{3}Z$",
        message = "The field startedSleepAt must be in the format yyyy-MM-ddTHH:mm:ss.SSSZ")
    val startedSleepAt: String?,

    @Schema(description = "The date and time when the user woke up", example = "2024-09-22T07:00:00.000Z")
    @field:NotNull(message = "The field wokeUpAt is required")
    @field:Pattern(regexp = "^[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}.[0-9]{3}Z$",
        message = "The field wokeUpAt must be in the format yyyy-MM-ddTHH:mm:ss.SSSZ")
    val wokeUpAt: String?,

    @Schema(description = "The user's feeling in the morning", example = "GOOD", allowableValues = ["GOOD", "OK", "BAD"])
    @field:NotNull(message = "The field morningFeeling is required")
    val morningFeeling: String?,
)