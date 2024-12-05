package adhil.assignment.tables

import adhil.assignment.config.DbConfig
import adhil.assignment.modals.Transaction

class TableTransaction {

    val connection = DbConfig.connection

    init {
        createTable()
    }

    fun createTable() {
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

    fun insertTransaction(transaction: Transaction) {
        val sql = """
            INSERT INTO transactions (order_id, amount, status) VALUES ('${transaction.orderId}', '${transaction.amount}', '${transaction.status}')
        """.trimIndent()
        connection.createStatement().execute(sql)
    }

    fun getTransactionsCount(startDate: String, endDate: String): Int {
        val sql = """
            SELECT COUNT(*) FROM transactions WHERE created_at BETWEEN '$startDate' AND '$endDate'
        """.trimIndent()
        val resultSet = connection.createStatement().executeQuery(sql)
        resultSet.next()
        return resultSet.getInt(1)
    }

    fun getRevenueByCreator(userId: String, startDate: String, endDate: String): Double {
        val courses = TableCourses().getCreatorCourses(userId).data
        var revenue = 0.0
        courses.forEach {
            revenue+= it.price*it.orderCount
        }
        return revenue
    }
}