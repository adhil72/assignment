package adhil.assignment.controllers

import adhil.assignment.config.AppConfig
import adhil.assignment.dtos.CreateOrderRequest
import adhil.assignment.dtos.CreateOrderResponse
import adhil.assignment.dtos.GetOrdersResponse
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
        return OrderService().createCourseOrder(request,courseId)
    }

    @GetMapping("/fetch")
    fun getOrders(request: HttpServletRequest, @RequestParam(required = false) page:Int=1, @RequestParam(required = false) limit:Int=10): GetOrdersResponse {
        permissive(listOf("customer"), request)
        return OrderService().getOrdersByUserId(request,page,limit)
    }

    @GetMapping("/payment/callback")
    fun paymentSuccess(@RequestParam orderId: String, @RequestParam success:Boolean): PaymentResponse {
        if (success){
            OrderService().paymentSuccess(orderId)
            return PaymentResponse("Payment Successful")
        }else{
            OrderService().paymentFailure(orderId)
            return PaymentResponse("Payment Failed")
        }
    }
}