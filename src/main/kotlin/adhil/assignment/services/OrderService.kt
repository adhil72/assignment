package adhil.assignment.services

import TableOrder
import adhil.assignment.config.AppConfig
import adhil.assignment.dtos.CreateOrderResponse
import adhil.assignment.modals.Order
import adhil.assignment.tables.TableUser
import java.util.*
import javax.servlet.http.HttpServletRequest

class OrderService {
    fun createCourseOrder(request: HttpServletRequest, courseId: String): CreateOrderResponse {
        val userId = request.getAttribute("userId") as String
        val order = TableOrder().insertOrder(
            Order(
                courseId = courseId, userId = userId, id = UUID.randomUUID().toString(), paymentStatus = false
            )
        )
        val paymentUrl = "${AppConfig.BASE_PATH}/order/payment?order=${order.id}"
        return CreateOrderResponse(data = order, paymentUrl = paymentUrl)
    }

    fun paymentSuccess(orderId: String):String{
        val order = TableOrder().getOrder(orderId)
        TableOrder().updateOrder(order.copy(paymentStatus = true, processing = false))
        TableUser().addCourse(order.courseId)
        return "Payment Successful"
    }

    fun paymentFailure(orderId: String):String{
        val order = TableOrder().getOrder(orderId)
        TableOrder().updateOrder(order.copy(paymentStatus = false, processing = false))
        return "Payment Failed"
    }
}