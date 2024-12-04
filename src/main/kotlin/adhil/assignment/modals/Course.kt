package adhil.assignment.modals

import java.util.UUID

data class Course(
    val id: String,
    val title: String,
    val description: String,
    val price: Double,
    val createdAt: String
) {
    constructor(title: String, description: String, price: Double) : this(UUID.randomUUID().toString(), title, description, price, "")
}