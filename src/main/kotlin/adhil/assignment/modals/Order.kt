package adhil.assignment.modals

import adhil.assignment.utils.timestamp
import java.time.LocalDateTime

data class Order(
    val id: String,
    val courseId: String,
    val userId: String,
    val createdAt: String = timestamp(LocalDateTime.now()).toString(),
    val paymentStatus: Boolean,
    val processing:Boolean = true
)