package adhil.assignment.dtos

import adhil.assignment.exceptions.CourseException
import adhil.assignment.modals.Course

data class CreateCourseRequest(
    val title: String,
    val description: String,
    val price: Double = -1.0
){
    init {
        if (title.isBlank()) throw CourseException("Title cannot be blank")
        if (description.isBlank()) throw CourseException("Description cannot be blank")
        if (price < 0) throw CourseException("Price cannot be negative")

    }
}

data class CreateCourseResponse(
    val message: String = "Course created successfully"
)

data class GetCourseRequest(
    val page: Int = 1,
    val limit: Int = 10,
    val search: String? = null
)

data class GetCoursesResponse(
    val data: List<Course>,
    val totalPages: Int = 1
)

