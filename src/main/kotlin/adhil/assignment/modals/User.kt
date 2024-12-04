package adhil.assignment.modals

data class User(
    val id: String, val email: String, val password: String, val role: String, val verified: Boolean = false
)