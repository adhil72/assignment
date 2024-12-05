package adhil.assignment.exceptions

class OrderException(message: String) : Exception(message) {
    override fun toString(): String {
        return "OrderException: $message"
    }
}