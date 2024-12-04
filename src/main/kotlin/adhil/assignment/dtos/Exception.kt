package adhil.assignment.dtos

import java.time.LocalDateTime

data class ErrorResponse(
    val message: String,
    val failed:Boolean = false
)