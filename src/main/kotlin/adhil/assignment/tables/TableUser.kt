package adhil.assignment.tables

import adhil.assignment.config.DbConfig
import adhil.assignment.dtos.GetUsersResponse
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
                verified BOOLEAN DEFAULT FALSE,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
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

    fun getUserById(id: String): User {
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

    fun addCourse(courseId: String, userId:String) {
        val user = getUserById(userId)
        val courses = user.courses.split(",").toMutableList()
        courses.add(courseId)
        val updateSQL = "UPDATE users SET courses = ? WHERE id = ?;"
        connection.prepareStatement(updateSQL).use { statement ->
            statement.setString(1, courses.joinToString(","))
            statement.setString(2, "1")
            statement.executeUpdate()
        }
    }

    fun getUsers(page: Int, limit: Int, search: String?): GetUsersResponse {
        val querySQL = if (search.isNullOrEmpty()) {
            "SELECT * FROM users LIMIT ? OFFSET ?;"
        } else {
            "SELECT * FROM users WHERE email LIKE ? OR role LIKE ? LIMIT ? OFFSET ?;"
        }

        connection.prepareStatement(querySQL).use { statement ->
            if (search.isNullOrEmpty()) {
                statement.setInt(1, limit)
                statement.setInt(2, (page - 1) * limit)
            } else {
                val searchTerm = "%$search%"
                statement.setString(1, searchTerm)
                statement.setString(2, searchTerm)
                statement.setInt(3, limit)
                statement.setInt(4, (page - 1) * limit)
            }

            val resultSet = statement.executeQuery()
            val users = mutableListOf<User>()
            while (resultSet.next()) {
                users.add(mapResultSetToUser(resultSet))
            }

            val countQuery = if (search.isNullOrEmpty()) {
                "SELECT COUNT(*) FROM users;"
            } else {
                "SELECT COUNT(*) FROM users WHERE email LIKE ? OR role LIKE ?;"
            }

            val count = connection.prepareStatement(countQuery).use { countStatement ->
                if (!search.isNullOrEmpty()) {
                    countStatement.setString(1, "%$search%")
                    countStatement.setString(2, "%$search%")
                }
                val countResultSet = countStatement.executeQuery()
                countResultSet.next()
                countResultSet.getInt(1)
            }

            val totalPages = if (count % limit == 0) count / limit else count / limit + 1
            return GetUsersResponse(data = users, totalPages = totalPages)
        }
    }

    fun getCreatorsCount(startDate: String, endDate: String): Int {

        val querySQL = "SELECT COUNT(*) FROM users WHERE role = 'creator' AND created_at BETWEEN ? AND ?;"
        connection.prepareStatement(querySQL).use { statement ->
            statement.setString(1, startDate)
            statement.setString(2, endDate)
            val resultSet = statement.executeQuery()
            resultSet.next()
            return resultSet.getInt(1)
        }
    }

    fun getCustomersCount(startDate: String, endDate: String): Int {
        val querySQL = "SELECT COUNT(*) FROM users WHERE role = 'customer' AND created_at BETWEEN ? AND ?;"
        connection.prepareStatement(querySQL).use { statement ->
            statement.setString(1, startDate)
            statement.setString(2, endDate)
            val resultSet = statement.executeQuery()
            resultSet.next()
            return resultSet.getInt(1)
        }
    }


}
