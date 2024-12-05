package adhil.assignment.controllers

import adhil.assignment.config.AppConfig
import adhil.assignment.dtos.CreateOrderRequest
import adhil.assignment.dtos.CreateOrderResponse
import adhil.assignment.dtos.PaymentResponse
import adhil.assignment.security.permissive
import adhil.assignment.services.OrderService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("${AppConfig.BASE_PATH}/order")
class OrderController {
    @PostMapping("/create")
    fun createOrder(request: HttpServletRequest, @RequestParam courseId: String):CreateOrderResponse {
        permissive(listOf("customer"), request)
        OrderService().createCourseOrder(request,courseId)
        return CreateOrderResponse()
    }

    @GetMapping("/payment/success")
    fun paymentSuccess(@RequestParam orderId: String): PaymentResponse {
        OrderService().paymentSuccess(orderId)
        return PaymentResponse("Payment Successful")
    }

    @GetMapping("/payment/failure")
    fun paymentFailure(@RequestParam orderId: String): PaymentResponse {
        OrderService().paymentFailure(orderId)
        return PaymentResponse("Payment Failed")
    }
}