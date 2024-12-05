package adhil.assignment.dtos

data class GetStatisticsRequest(
    val startDate: String,
    val endDate: String
){
    init {
        if (startDate.split("-").size != 3) throw Exception("Invalid Start Date")
        if (endDate.split("-").size != 3) throw Exception("Invalid End Date")
    }
}

data class GetStatisticsAdminResponse(
    val totalOrders: Int,
    val totalCreators: Int,
    val totalCustomers: Int,
    val totalCourses: Int,
    val totalTransactions: Int
)

data class GetStatisticsCreatorResponse(
    val totalOrders: Int,
    val totalCourses: Int,
    val totalRevenue: Int,
)

data class GetStatisticsCustomerResponse(
    val totalOrders: Int,
    val totalCourses: Int
)