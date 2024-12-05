package adhil.assignment.utils

fun validateBody(body: Class<Any>, fields: List<String>) {
    val bodyFields = body.declaredFields.map { it.name }
    println(bodyFields)
    fields.forEach {
        if (!bodyFields.contains(it)) {
            throw Exception("Field $it is missing in request body")
        }
    }
}