package adhil.assignment.dtos

import adhil.assignment.modals.User

data class SignUpRequest(
    val email: String,
    val password: String,
    val role: String
)

data class SignUpResponse(
    val message: String = "User signed up successfully.",
)

data class VerifyResponse(
    val message: String = "Email verified successfully.",
)

data class SigninRequest(
    val email: String,
    val password: String
)

data class SigninResponse(
    val message: String = "User signed in successfully.",
    val token: String
)

data class ResendVerificationResponse(
    val message: String = "Verification email sent successfully."
)

data class GetUsersResponse(
    val data: List<User>,
    val totalPages: Int
)