package adhil.assignment.modals

import java.time.LocalDateTime

data class Exception (
    val id: String,
    val createdAt: LocalDateTime,
    val message: String,
    val stackTrace: String
)