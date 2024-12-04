package adhil.assignment.exceptions

class UserAlreadyExistsException(override val message: String): Exception(message) {
    override fun toString(): String {
        return "UserAlreadyExistsException: $message"
    }
}

class InvalidVerificationLinkException(override val message: String): Exception(message) {
    override fun toString(): String {
        return "InvalidVerificationLinkException: $message"
    }
}

class InvalidCredentialsException(override val message: String): Exception(message) {
    override fun toString(): String {
        return "InvalidCredentialsException: $message"
    }
}

class EmailNotVerifiedException(override val message: String): Exception(message) {
    override fun toString(): String {
        return "EmailNotVerifiedException: $message"
    }
}