package adhil.assignment.tables

import adhil.assignment.config.DbConfig
import adhil.assignment.utils.timestamp
import java.time.LocalDateTime
import java.util.*

class TableAccessToken {
    val connection = DbConfig.connection

    init {
        createTables()
    }

    fun createTables() {
        val statement = connection.createStatement()
        statement.execute("CREATE TABLE IF NOT EXISTS access_tokens (id VARCHAR(255) PRIMARY KEY, user_id VARCHAR(255), expiry_date DATETIME)")
    }

    fun createAccessToken(userId: String): String {
        val statement = connection.createStatement()
        val id = UUID.randomUUID().toString()
        val expiryDate = timestamp(LocalDateTime.now().plusDays(30))
        statement.execute(
            "INSERT INTO access_tokens (id, user_id, expiry_date) VALUES ('$id', '$userId', '${expiryDate}')"
        )
        return id
    }

    fun getUserId(id: String): String {
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT user_id FROM access_tokens WHERE id = '$id'")
        resultSet.next()
        return resultSet.getString("user_id")
    }

    fun exists(id: String): Boolean {
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT * FROM access_tokens WHERE id = '$id'")
        return resultSet.next()
    }

    fun isExpired(id: String): Boolean {
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT expiry_date FROM access_tokens WHERE id = '$id'")
        resultSet.next()
        val expiryDate = resultSet.getTimestamp("expiry_date").toLocalDateTime()
        return expiryDate.isBefore(LocalDateTime.now())
    }

    fun validateToken(token: String): String {
        if (!exists(token)) {
            throw Exception("Invalid token.")
        }
        if (isExpired(token)) {
            throw Exception("Expired token.")
        }
        return getUserId(token)
    }
}