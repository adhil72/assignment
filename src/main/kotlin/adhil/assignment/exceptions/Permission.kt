package adhil.assignment.exceptions

class PermissionDeniedException(message: String) : Exception(message) {
    override fun toString(): String {
        return "PermissionDeniedException: $message"
    }
}