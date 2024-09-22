package com.noom.interview.fullstack.sleep.rest.controller

import com.noom.interview.fullstack.sleep.exception.FieldValueInvalidException
import com.noom.interview.fullstack.sleep.exception.SleepLogNotFoundException
import com.noom.interview.fullstack.sleep.rest.dto.CreateSleepLogRequestDto
import com.noom.interview.fullstack.sleep.rest.dto.ErrorDto
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ApiResponse(responseCode = "400", description = "Bad Request", content = [
        Content(mediaType = "application/json", schema = Schema(implementation = ErrorDto::class))
    ])
    fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException): ErrorDto {
        return ErrorDto(ex.bindingResult.allErrors.map { it.defaultMessage }.joinToString(", "))
    }

    @ExceptionHandler(FieldValueInvalidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ApiResponse(responseCode = "400", description = "Bad Request", content = [
        Content(mediaType = "application/json", schema = Schema(implementation = ErrorDto::class))
    ])
    fun handleCreateSleepLogException(e: FieldValueInvalidException) =
        ErrorDto(e.message ?: "Unknown error")

    @ExceptionHandler(SleepLogNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ApiResponse(responseCode = "404", description = "Sleep Log not found", content = [
    Content(mediaType = "application/json", schema = Schema(implementation = CreateSleepLogRequestDto::class))
    ])
    fun handleSleepLogNotFoundException(e: SleepLogNotFoundException) =
        ErrorDto(e.message)

}