package com.noom.interview.fullstack.sleep.rest.controller

import com.noom.interview.fullstack.sleep.rest.dto.CreateSleepLogRequestDto
import com.noom.interview.fullstack.sleep.rest.dto.CreateSleepLogResponseDto
import com.noom.interview.fullstack.sleep.rest.dto.SleepLogResponseDto
import com.noom.interview.fullstack.sleep.rest.mapper.createSleepLogRequestDtoToSleepLog
import com.noom.interview.fullstack.sleep.rest.mapper.sleepLogToSleepLogResponseDto
import com.noom.interview.fullstack.sleep.service.SleepLogService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/sleep-log")
class SleepLogController(
    private val sleepLogService: SleepLogService,
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a sleep log for the last night")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Sleep Log created", content = [
            Content(mediaType = "application/json", schema = Schema(implementation = CreateSleepLogRequestDto::class))
        ]),
    ])
    fun createSleepLog(
        @RequestHeader username: String,
        @RequestBody @Valid dto: CreateSleepLogRequestDto
    ): CreateSleepLogResponseDto {
        val sleepLog = createSleepLogRequestDtoToSleepLog(dto, username)
        val createdId = sleepLogService.createSleepLog(sleepLog)
        return CreateSleepLogResponseDto(createdId)
    }

    @GetMapping("/last-night")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get the sleep log for the last night")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Sleep Log found", content = [
            Content(mediaType = "application/json", schema = Schema(implementation = CreateSleepLogRequestDto::class))
        ]),
    ])
    fun getLastNightSleepLog(@RequestHeader username: String): SleepLogResponseDto {
        val sleepLog = sleepLogService.findLastNightSleepLog(username)
        return sleepLogToSleepLogResponseDto(sleepLog)
    }
}