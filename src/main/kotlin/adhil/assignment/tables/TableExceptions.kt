package adhil.assignment.tables

import adhil.assignment.config.DbConfig
import adhil.assignment.utils.timestamp
import java.time.LocalDateTime
import java.util.UUID

class TableExceptions {
    val connection = DbConfig.connection

    init {
        createTable()
    }

    fun createTable() {
        val statement = connection.createStatement()
        statement.execute(
            """
            CREATE TABLE IF NOT EXISTS exceptions (
                id VARCHAR(255) PRIMARY KEY,
                message TEXT NOT NULL,
                stack_trace TEXT NOT NULL,
                created_at TIMESTAMP NOT NULL
            )
            """.trimIndent()
        )
    }

    fun insertException(message: String, stackTrace: String) {
        val statement = connection.createStatement()
        val createdAt = LocalDateTime.now()
        statement.execute(
            """
            INSERT INTO exceptions (id, message, stack_trace, created_at) 
            VALUES ('${UUID.randomUUID()}', '$message', '$stackTrace', '${timestamp(createdAt)}')
            """.trimIndent()
        )
    }

    fun getExceptions(): List<adhil.assignment.modals.Exception> {
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT * FROM exceptions")
        val exceptions = mutableListOf<adhil.assignment.modals.Exception>()
        while (resultSet.next()) {
            exceptions.add(
                adhil.assignment.modals.Exception(
                    id = resultSet.getString("id"),
                    message = resultSet.getString("message"),
                    stackTrace = resultSet.getString("stack_trace"),
                    createdAt = resultSet.getTimestamp("created_at").toLocalDateTime(),
                )
            )
        }
        return exceptions
    }
}