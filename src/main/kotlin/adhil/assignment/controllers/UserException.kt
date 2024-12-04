package adhil.assignment.controllers

import adhil.assignment.dtos.ErrorResponse
import adhil.assignment.exceptions.EmailNotVerifiedException
import adhil.assignment.exceptions.InvalidCredentialsException
import adhil.assignment.exceptions.InvalidVerificationLinkException
import adhil.assignment.exceptions.UserAlreadyExistsException
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime

@RestControllerAdvice
@Order(1)
class UserException {
    @ExceptionHandler(UserAlreadyExistsException::class)
    fun handleUserAlreadyExistsException(ex: UserAlreadyExistsException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            timestamp = LocalDateTime.now().toString(),
            message = ex.message,
        )
        return ResponseEntity(errorResponse, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(InvalidVerificationLinkException::class)
    fun handleInvalidVerificationLinkException(ex: InvalidVerificationLinkException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            timestamp = LocalDateTime.now().toString(),
            message = ex.message,
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(InvalidCredentialsException::class)
    fun handleInvalidCredentialsException(ex: InvalidCredentialsException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            timestamp = LocalDateTime.now().toString(),
            message = ex.message,
        )
        return ResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(EmailNotVerifiedException::class)
    fun handleEmailNotVerifiedException(ex: EmailNotVerifiedException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            timestamp = LocalDateTime.now().toString(),
            message = ex.message,
        )
        return ResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED)
    }

}