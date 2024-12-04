package adhil.assignment.tables

import adhil.assignment.config.DbConfig
import adhil.assignment.modals.Verification
import java.time.LocalDateTime
import java.util.UUID

class TableVerification {
    val connection = DbConfig.connection

    init {
        createTables()
    }

    fun createTables(){
        val statement = connection.createStatement()
        statement.execute("CREATE TABLE IF NOT EXISTS verification (id VARCHAR(255) PRIMARY KEY, email VARCHAR(255), expiry TIMESTAMP)")
    }

    fun deleteById(id: String){
        val statement = connection.createStatement()
        statement.execute("DELETE FROM verification WHERE id = '$id'")
    }

    fun createVerification(email: String):Verification{
        val statement = connection.createStatement()
        val id = UUID.randomUUID().toString()
        val expiry = LocalDateTime.now().plusMinutes(10)
        statement.execute("INSERT INTO verification (id, email, expiry) VALUES ('$id', '$email', '$expiry')")
        return Verification(id, email)
    }

    fun exists(id: String): Boolean{
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT * FROM verification WHERE id = '$id'")
        return resultSet.next()
    }

    fun exists(id: String, email: String): Boolean{
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT * FROM verification WHERE id = '$id' AND email = '$email'")
        return resultSet.next()
    }

    fun isExpired(id: String, email: String): Boolean {
    val statement = connection.createStatement()
    val resultSet = statement.executeQuery("SELECT * FROM verification" +
            " WHERE id = '$id' AND email = '$email' AND expiry < datetime('now')")
    return resultSet.next()
}
}