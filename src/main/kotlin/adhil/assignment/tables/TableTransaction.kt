package adhil.assignment.tables

import adhil.assignment.config.DbConfig
import adhil.assignment.modals.Transaction

class TableTransaction {

    val connection = DbConfig.connection

    init {
        createTable()
    }

    fun createTable(){
        val sql = """
            CREATE TABLE IF NOT EXISTS transactions (
                id SERIAL PRIMARY KEY,
                order_id INT NOT NULL,
                amount INT NOT NULL,
                status VARCHAR(255) NOT NULL,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
        """.trimIndent()
        connection.createStatement().execute(sql)
    }

    fun insertTransaction(transaction: Transaction){
        val sql = """
            INSERT INTO transactions (order_id, amount, status) VALUES ('${transaction.orderId}', '${transaction.amount}', '${transaction.status}')
        """.trimIndent()
        connection.createStatement().execute(sql)
    }
}