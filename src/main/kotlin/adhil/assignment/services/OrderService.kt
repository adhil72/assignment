package adhil.assignment.services

import adhil.assignment.tables.TableOrder
import adhil.assignment.config.AppConfig
import adhil.assignment.dtos.CreateOrderResponse
import adhil.assignment.dtos.GetOrderRequest
import adhil.assignment.dtos.GetOrdersResponse
import adhil.assignment.modals.Order
import adhil.assignment.modals.Transaction
import adhil.assignment.tables.TableCourses
import adhil.assignment.tables.TableTransaction
import adhil.assignment.tables.TableUser
import java.util.*
import javax.servlet.http.HttpServletRequest

class OrderService {
    fun createCourseOrder(request: HttpServletRequest, courseId: String): CreateOrderResponse {
        val userId = request.getAttribute("uid") as String
        val order = TableOrder().insertOrder(
            Order(
                courseId = courseId, userId = userId, id = UUID.randomUUID().toString(), paymentStatus = false
            )
        )
        val callbackUrl = "${AppConfig.BASE_PATH}/order/payment/callback?orderId=${order.id}&success=<TRUE_OR_FALSE>"
        return CreateOrderResponse(data = order, callbackUrl = callbackUrl)
    }

    fun paymentSuccess(orderId: String):String{
        val order = TableOrder().getOrder(orderId)
        TableOrder().updateOrder(order.copy(paymentStatus = true, processing = false))
        TableUser().addCourse(order.courseId,order.userId)
        TableCourses().incrementCourseBuyCount(order.courseId)
        TableTransaction().insertTransaction(Transaction(
            orderId = orderId,
            amount = 100,
            status = "success"
        ))
        return "Payment Successful"
    }

    fun paymentFailure(orderId: String):String{
        val order = TableOrder().getOrder(orderId)
        TableOrder().updateOrder(order.copy(paymentStatus = false, processing = false))
        return "Payment Failed"
    }

    fun getOrdersByUserId(request: HttpServletRequest, page: Int, limit: Int): GetOrdersResponse {
        val userId = request.getAttribute("uid") as String
        return TableOrder().getOrdersByUserId(userId, GetOrderRequest(page, limit))
    }
}