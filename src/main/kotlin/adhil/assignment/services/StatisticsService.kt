package adhil.assignment.services

import adhil.assignment.tables.TableOrder
import adhil.assignment.dtos.GetStatisticsAdminResponse
import adhil.assignment.dtos.GetStatisticsCreatorResponse
import adhil.assignment.dtos.GetStatisticsCustomerResponse
import adhil.assignment.dtos.GetStatisticsRequest
import adhil.assignment.tables.TableCourses
import adhil.assignment.tables.TableTransaction
import adhil.assignment.tables.TableUser
import javax.servlet.http.HttpServletRequest

class StatisticsService {
    fun getStatistics(request: HttpServletRequest,requestStatistics: GetStatisticsRequest):Any {
        val role = request.getAttribute("role") as String
        val userId = request.getAttribute("uid") as String
        return when(role) {
            "admin" -> getAdminStatistics(requestStatistics)
            "creator" -> getCreatorStatistics(userId, requestStatistics)
            "customer" -> getCustomerStatistics(userId, requestStatistics)
            else -> {
                throw Exception("Invalid Role")
            }
        }
    }

    private fun getAdminStatistics(requestStatistics: GetStatisticsRequest):GetStatisticsAdminResponse {
        val totalOrders = TableOrder().getOrdersCount(startDate = requestStatistics.startDate, endDate = requestStatistics.endDate)
        val totalCreators = TableUser().getCreatorsCount(startDate = requestStatistics.startDate, endDate = requestStatistics.endDate)
        val totalCustomers = TableUser().getCustomersCount(startDate = requestStatistics.startDate, endDate = requestStatistics.endDate)
        val totalCourses = TableCourses().getCoursesCount(startDate = requestStatistics.startDate, endDate = requestStatistics.endDate)
        val totalTransactions = TableTransaction().getTransactionsCount(startDate = requestStatistics.startDate, endDate = requestStatistics.endDate)
        return GetStatisticsAdminResponse(totalOrders, totalCreators, totalCustomers, totalCourses, totalTransactions)
    }

    private fun getCreatorStatistics(userId: String, requestStatistics: GetStatisticsRequest):GetStatisticsCreatorResponse {
        val totalOrders = TableOrder().getOrdersCountByCreator(userId, requestStatistics.startDate, requestStatistics.endDate)
        val totalRevenue = TableTransaction().getRevenueByCreator(userId, requestStatistics.startDate, requestStatistics.endDate)
        val totalCourses = TableCourses().getCoursesCountByCreator(userId, requestStatistics.startDate, requestStatistics.endDate)
        return GetStatisticsCreatorResponse(totalOrders, totalCourses, totalRevenue)
    }

    private fun getCustomerStatistics(userId: String, requestStatistics: GetStatisticsRequest):GetStatisticsCustomerResponse {
        val totalOrders = TableOrder().getOrdersCountByCustomer(userId, requestStatistics.startDate, requestStatistics.endDate)
        val totalCourses = TableCourses().getCoursesCountByCustomer(userId, requestStatistics.startDate, requestStatistics.endDate)
        return GetStatisticsCustomerResponse(totalOrders, totalCourses)
    }
}