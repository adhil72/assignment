package adhil.assignment.utils

import java.sql.Timestamp
import java.time.LocalDateTime

fun timestamp(localDateTime: LocalDateTime): Timestamp {
    return Timestamp.valueOf(localDateTime)
}