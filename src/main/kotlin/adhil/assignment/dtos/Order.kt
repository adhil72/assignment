package adhil.assignment.dtos

import adhil.assignment.modals.Order

data class GetOrderRequest(
    val page: Int = 1,
    val limit: Int = 10,
)

data class GetOrdersResponse(
    val data: List<Order>,
    val totalPages: Int = 1
)

data class CreateOrderResponse(
    val data: Order?=null,
    val message: String = "Order created successfully",
    val paymentUrl: String?=null
)

data class CreateOrderRequest(
    val courseId: String
)

data class PaymentResponse(
    val message: String
)