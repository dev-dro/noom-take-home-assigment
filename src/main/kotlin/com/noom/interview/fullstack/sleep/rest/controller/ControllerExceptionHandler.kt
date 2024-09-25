package com.noom.interview.fullstack.sleep.rest.controller

import com.noom.interview.fullstack.sleep.exception.FieldValueInvalidException
import com.noom.interview.fullstack.sleep.exception.SleepLogNotFoundException
import com.noom.interview.fullstack.sleep.rest.dto.ErrorResponseDto
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
        Content(mediaType = "application/json", schema = Schema(implementation = ErrorResponseDto::class))
    ])
    fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException): ErrorResponseDto {
        return ErrorResponseDto(ex.bindingResult.allErrors.map { it.defaultMessage }.joinToString(", "))
    }

    @ExceptionHandler(FieldValueInvalidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ApiResponse(responseCode = "400", description = "Bad Request", content = [
        Content(mediaType = "application/json", schema = Schema(implementation = ErrorResponseDto::class))
    ])
    fun handleCreateSleepLogException(e: FieldValueInvalidException) =
        ErrorResponseDto(e.message ?: "Unknown error")

    @ExceptionHandler(SleepLogNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ApiResponse(responseCode = "404", description = "Sleep Log not found", content = [
        Content(mediaType = "application/json", schema = Schema(implementation = ErrorResponseDto::class))
    ])
    fun handleSleepLogNotFoundException(e: SleepLogNotFoundException) =
        ErrorResponseDto(e.message)

}