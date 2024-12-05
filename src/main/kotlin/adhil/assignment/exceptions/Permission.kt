package adhil.assignment.exceptions

open class PermissionException(message: String) : Exception(message) {
    override fun toString(): String {
        return "PermissionException: $message"
    }
}

class PermissionDeniedException(message: String) : PermissionException(message) {
    override fun toString(): String {
        return "PermissionDeniedException: $message"
    }
}