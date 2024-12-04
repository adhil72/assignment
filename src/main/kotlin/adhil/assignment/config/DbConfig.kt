package adhil.assignment.config

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class DbConfig {
    companion object{
        private val URL = "jdbc:sqlite:./digital_course_marketplace.db"
        lateinit var connection: Connection

        private fun dropTable(tableName: String) {
            val sql = "DROP TABLE IF EXISTS $tableName"
            try {
                val statement = connection.createStatement()
                statement.executeUpdate(sql)
                println("Table $tableName dropped successfully.")
            } catch (e: SQLException) {
                println("Failed to drop table $tableName: ${e.message}")
            }
        }

        private fun dropAllTables() {
            val tables = listOf("access_tokens", "exceptions", "users","verification") // Add all your table names here
            for (table in tables) {
                dropTable(table)
            }
        }

        fun connect(): Connection? {
            return try {
                connection = DriverManager.getConnection(URL)
                dropAllTables()
                connection
            } catch (e: SQLException) {
                println("Connection failed: ${e.message}")
                null
            }
        }
    }
}