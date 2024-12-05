package adhil.assignment.tables

import adhil.assignment.config.DbConfig
import adhil.assignment.modals.User
import java.sql.Connection
import java.sql.ResultSet

class TableUser {

    val connection: Connection = DbConfig.connection
    val roles = listOf("admin", "creator", "customer")

    init {
        createTableIfNotExists()
    }

    private fun createTableIfNotExists() {
        val createTableSQL = """
            CREATE TABLE IF NOT EXISTS users (
                id TEXT PRIMARY KEY,
                email TEXT NOT NULL UNIQUE,
                password TEXT NOT NULL,
                role TEXT NOT NULL,
                courses TEXT DEFAULT '',
                verified BOOLEAN DEFAULT FALSE
            );
        """.trimIndent()

        connection.createStatement().use { it.execute(createTableSQL) }
    }

    fun insertUser(user: User) {
        if (!roles.contains(user.role)) throw IllegalArgumentException("Invalid role: ${user.role}")
        val insertSQL = "INSERT INTO users (id, email, password, role) VALUES (?, ?, ?, ?);"
        connection.prepareStatement(insertSQL).use { statement ->
            statement.setString(1, user.id)
            statement.setString(2, user.email)
            statement.setString(3, user.password)
            statement.setString(4, user.role)
            statement.executeUpdate()
        }
    }

    fun getUserByEmail(email: String): User? {
        val querySQL = "SELECT * FROM users WHERE email = ?;"
        connection.prepareStatement(querySQL).use { statement ->
            statement.setString(1, email)
            val resultSet = statement.executeQuery()
            if (resultSet.next()) {
                return mapResultSetToUser(resultSet)
            }
        }
        return null
    }

    fun deleteUser(userId: String): Boolean {
        val deleteSQL = "DELETE FROM users WHERE id = ?;"
        connection.prepareStatement(deleteSQL).use { statement ->
            statement.setString(1, userId)
            return statement.executeUpdate() > 0
        }
    }

    fun exists(email: String): Boolean {
        val querySQL = "SELECT * FROM users WHERE email = ?;"
        connection.prepareStatement(querySQL).use { statement ->
            statement.setString(1, email)
            val resultSet = statement.executeQuery()
            return resultSet.next()
        }
    }

    fun verifyEmail(email: String): String? {
        val updateSQL = "UPDATE users SET verified = TRUE WHERE email = ?;"
        connection.prepareStatement(updateSQL).use { statement ->
            statement.setString(1, email)
            statement.executeUpdate()
        }
        return null
    }

    fun isVerified(email: String): Boolean {
        val querySQL = "SELECT verified FROM users WHERE email = ?;"
        connection.prepareStatement(querySQL).use { statement ->
            statement.setString(1, email)
            val resultSet = statement.executeQuery()
            return if (resultSet.next()) {
                resultSet.getBoolean("verified")
            } else {
                false
            }
        }
    }

    private fun mapResultSetToUser(resultSet: ResultSet): User {
        return User(
            id = resultSet.getString("id"),
            email = resultSet.getString("email"),
            password = resultSet.getString("password"),
            role = resultSet.getString("role"),
            verified = resultSet.getBoolean("verified")
        )
    }

    fun getUserById(id:String): User {
        val querySQL = "SELECT * FROM users WHERE id = ?;"
        connection.prepareStatement(querySQL).use { statement ->
            statement.setString(1, id)
            val resultSet = statement.executeQuery()
            if (resultSet.next()) {
                return mapResultSetToUser(resultSet)
            }
        }
        throw Exception("User not found")
    }

    fun addCourse(courseId: String) {
        val user = getUserById("1")
        val courses = user.courses.split(",").toMutableList()
        courses.add(courseId)
        val updateSQL = "UPDATE users SET courses = ? WHERE id = ?;"
        connection.prepareStatement(updateSQL).use { statement ->
            statement.setString(1, courses.joinToString(","))
            statement.setString(2, "1")
            statement.executeUpdate()
        }
    }
}
