package adhil.assignment.modals

import java.util.UUID

data class Transaction(
    val id:String,
    val orderId: String,
    val amount: Int,
    val status:String,
    val createdAt:String
){
    constructor(orderId: String,amount: Int,status: String):this(UUID.randomUUID().toString(),orderId,amount,status,"")
}