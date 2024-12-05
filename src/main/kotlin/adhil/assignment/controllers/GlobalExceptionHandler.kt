package adhil.assignment.controllers

import adhil.assignment.dtos.ErrorResponse
import adhil.assignment.tables.TableExceptions
import com.fasterxml.jackson.databind.exc.ValueInstantiationException
import org.springframework.boot.json.JsonParseException
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime

@RestControllerAdvice
@Order(100)
class GlobalExceptionHandler {
    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<ErrorResponse> {
        println(ex.javaClass.name)
        val errorResponse = ErrorResponse(
            message = ex.message?: "Internal Server Error",
            failed = true
        )
        TableExceptions().insertException(errorResponse.message,ex.stackTraceToString())
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleJsonParseException(ex: HttpMessageNotReadableException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            message = ex.message?.split("problem:")?.last()?.split("\n at")?.first()?:"Invalid JSON",
            failed = true
        )
        TableExceptions().insertException(errorResponse.message,ex.stackTraceToString())
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }
}