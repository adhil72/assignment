package adhil.assignment.exceptions


open class UserException(override val message: String): Exception(message) {
    override fun toString(): String {
        return "UserException: $message"
    }
}

class UserAlreadyExistsException(override val message: String): UserException(message) {
    override fun toString(): String {
        return "UserAlreadyExistsException: $message"
    }
}

class InvalidVerificationLinkException(override val message: String): UserException(message) {
    override fun toString(): String {
        return "InvalidVerificationLinkException: $message"
    }
}

class InvalidCredentialsException(override val message: String): UserException(message) {
    override fun toString(): String {
        return "InvalidCredentialsException: $message"
    }
}

class EmailVerificationException(override val message: String): UserException(message) {
    override fun toString(): String {
        return "EmailVerificationException: $message"
    }
}

class PasswordException(override val message: String): UserException(message) {
    override fun toString(): String {
        return "PasswordException: $message"
    }
}

class EmailException(override val message: String): UserException(message) {
    override fun toString(): String {
        return "EmailException: $message"
    }
}