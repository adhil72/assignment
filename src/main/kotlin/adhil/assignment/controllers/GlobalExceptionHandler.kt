package adhil.assignment.controllers

import adhil.assignment.dtos.ErrorResponse
import adhil.assignment.exceptions.CourseException
import adhil.assignment.exceptions.OrderException
import adhil.assignment.exceptions.PermissionException
import adhil.assignment.exceptions.UserException
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
class GlobalExceptionHandler {
    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<ErrorResponse> {
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
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(UserException::class)
    fun handleUserException(ex: UserException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            message = ex.message?: "Internal Server Error",
            failed = true
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(OrderException::class)
    fun handleOrderException(ex: OrderException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            message = ex.message?: "Internal Server Error",
            failed = true
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(CourseException::class)
    fun handleCourseException(ex: CourseException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            message = ex.message?: "Internal Server Error",
            failed = true
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(PermissionException::class)
    fun handlePermissionException(ex: PermissionException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            message = ex.message?: "Internal Server Error",
            failed = true
        )
        return ResponseEntity(errorResponse, HttpStatus.FORBIDDEN)
    }
}