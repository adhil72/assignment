package adhil.assignment.exceptions

class CourseException(message: String) : Exception(message) {
    override fun toString(): String {
        return "CourseException: $message"
    }
}