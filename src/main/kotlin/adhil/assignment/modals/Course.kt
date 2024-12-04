package adhil.assignment.modals

import java.util.UUID

data class Course(
    val id: String,
    val title: String,
    val description: String,
    val price: Double,
    val createdAt: String,
    val createdBy: String?
) {
    constructor(title: String, description: String, price: Double, createdBy: String) : this(UUID.randomUUID().toString(), title, description, price, "",createdBy)
}