package adhil.assignment.dtos

import adhil.assignment.modals.Course

data class CreateCourseRequest(
    val title: String,
    val description: String,
    val price: Double
)

data class CreateCourseResponse(
    val data: Course,
    val message: String = "Course created successfully"
)

data class GetCourseRequest(
    val page: Int = 1,
    val limit: Int = 10
)

data class GetCoursesResponse(
    val data: List<Course>,
    val totalPages: Int = 1
)

data class GetCreatorCoursesRequest(
    val page: Int = 1,
    val limit: Int = 10,
    val creatorId: String
)