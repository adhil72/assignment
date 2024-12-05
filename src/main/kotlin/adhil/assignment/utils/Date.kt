package adhil.assignment.utils

import java.sql.Timestamp
import java.time.LocalDateTime

fun timestamp(localDateTime: LocalDateTime): Timestamp {
    return Timestamp.valueOf(localDateTime)
}

fun timestamp(date:String): Timestamp {
    val dateSplit = date.split("-")
    val localDateTime = LocalDateTime.of(dateSplit[2].toInt(),dateSplit[1].toInt(),dateSplit[0].toInt(),0,0)
    return timestamp(localDateTime)
}