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

    fun getRevenueByCreator(userId: String, startDate: String, endDate: String): Int {
        return try {
            val courses = TableCourses().getCreatorCourses(userId).data
            val courseIds = courses.map { it.id }
            if (courseIds.isEmpty()) {
                return 0
            }
            val placeholders = courseIds.joinToString(",") { "?" }
            val sql = """
            SELECT SUM(amount) FROM transactions WHERE order_id IN (SELECT id FROM orders WHERE course_id IN ($placeholders)) AND created_at BETWEEN ? AND ?
        """.trimIndent()
            val preparedStatement = connection.prepareStatement(sql)
            courseIds.forEachIndexed { index, courseId ->
                preparedStatement.setString(index + 1, courseId)
            }
            preparedStatement.setString(courseIds.size + 1, startDate)
            preparedStatement.setString(courseIds.size + 2, endDate)
            val resultSet = preparedStatement.executeQuery()
            resultSet.next()
            resultSet.getInt(1)
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }
}