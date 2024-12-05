package adhil.assignment.config

import adhil.assignment.tables.createTables
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class DbConfig {
    companion object{
        private val URL = "jdbc:sqlite:./digital_course_marketplace.db"
        lateinit var connection: Connection

        fun connect(): Connection? {
            return try {
                connection = DriverManager.getConnection(URL)
                createTables()
                connection
            } catch (e: SQLException) {
                println("Connection failed: ${e.message}")
                null
            }
        }
    }
}