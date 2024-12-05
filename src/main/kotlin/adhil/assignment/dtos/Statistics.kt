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