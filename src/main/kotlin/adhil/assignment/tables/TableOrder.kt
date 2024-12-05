import adhil.assignment.config.DbConfig
import adhil.assignment.dtos.GetOrderRequest
import adhil.assignment.dtos.GetOrdersResponse
import adhil.assignment.modals.Order
import java.time.format.DateTimeFormatter

class TableOrder {
    val connection = DbConfig.connection

    init {
        createTable()
    }

    fun createTable() {
        val sql = """
            CREATE TABLE IF NOT EXISTS orders (
                id VARCHAR(255) PRIMARY KEY,
                course_id VARCHAR(255) NOT NULL,
                user_id VARCHAR(255) NOT NULL,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                payment_status BOOLEAN DEFAULT FALSE,
                processing_status BOOLEAN DEFAULT TRUE
            )
        """.trimIndent()
        connection.createStatement().execute(sql)
    }

    fun insertOrder(order: Order): Order {
        val sql = """
            INSERT INTO orders (id, course_id, user_id) VALUES (?, ?, ?)
        """.trimIndent()
        val preparedStatement = connection.prepareStatement(sql)
        preparedStatement.setString(1, order.id)
        preparedStatement.setString(2, order.courseId)
        preparedStatement.setString(3, order.userId)
        preparedStatement.executeUpdate()
        return order
    }

    fun updatePaymentStatus(orderId: String) {
        val sql = """
            UPDATE orders SET payment_status = TRUE WHERE id = ?
        """.trimIndent()
        val preparedStatement = connection.prepareStatement(sql)
        preparedStatement.setString(1, orderId)
        preparedStatement.executeUpdate()
    }

    fun getOrdersByUserId(userId: String, getOrderRequest: GetOrderRequest): GetOrdersResponse {
        val page = getOrderRequest.page
        val limit = getOrderRequest.limit
        val sql = """
            SELECT * FROM orders WHERE user_id = ? LIMIT ? OFFSET ?
        """.trimIndent()
        val preparedStatement = connection.prepareStatement(sql)
        preparedStatement.setString(1, userId)
        preparedStatement.setInt(2, limit)
        preparedStatement.setInt(3, (page - 1) * limit)
        val resultSet = preparedStatement.executeQuery()
        val orders = mutableListOf<Order>()
        while (resultSet.next()) {
            orders.add(
                Order(
                    id = resultSet.getString("id"),
                    courseId = resultSet.getString("course_id"),
                    userId = resultSet.getString("user_id"),
                    paymentStatus = resultSet.getBoolean("payment_status"),
                    createdAt = resultSet.getTimestamp("created_at").toLocalDateTime()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    processing = resultSet.getBoolean("processing_status")
                )
            )
        }

        val totalCount =
            connection.createStatement().executeQuery("SELECT COUNT(*) FROM orders WHERE user_id = '$userId'").getInt(1)
        val totalPages = if (totalCount % limit == 0) totalCount / limit else totalCount / limit + 1
        return GetOrdersResponse(orders, totalPages)
    }

    fun getOrder(orderId: String): Order {
        val sql = """
            SELECT * FROM orders WHERE id = ?
        """.trimIndent()
        val preparedStatement = connection.prepareStatement(sql)
        preparedStatement.setString(1, orderId)
        val resultSet = preparedStatement.executeQuery()
        resultSet.next()
        return Order(
            id = resultSet.getString("id"),
            courseId = resultSet.getString("course_id"),
            userId = resultSet.getString("user_id"),
            paymentStatus = resultSet.getBoolean("payment_status"),
            createdAt = resultSet.getTimestamp("created_at").toLocalDateTime()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        )
    }

    fun updateOrder(newOrder: Order) {
        println("updating order : $newOrder")
        val sql = """
        UPDATE orders SET payment_status = ?, processing_status = ? WHERE id = ?
    """.trimIndent()
        val preparedStatement = connection.prepareStatement(sql)
        preparedStatement.setBoolean(1, newOrder.paymentStatus)
        preparedStatement.setBoolean(2, newOrder.processing)
        preparedStatement.setString(3, newOrder.id)
        preparedStatement.executeUpdate()
    }
}