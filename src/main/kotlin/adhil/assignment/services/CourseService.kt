package adhil.assignment.services

import adhil.assignment.dtos.CreateCourseRequest
import adhil.assignment.dtos.CreateCourseResponse
import adhil.assignment.dtos.GetCourseRequest
import adhil.assignment.dtos.GetCoursesResponse
import adhil.assignment.modals.Course
import adhil.assignment.tables.TableCourses
import javax.servlet.http.HttpServletRequest

class CourseService {
    fun getCreatorCourses(request: HttpServletRequest, limit: Int, page: Int): GetCoursesResponse {
        val userId = request.getAttribute("uid") as String
        return TableCourses().getCreatorCourses(userId, limit, page)
    }

    fun createCourse(request: HttpServletRequest, createCourseRequest: CreateCourseRequest): CreateCourseResponse {
        val userId = request.getAttribute("uid") as String
        val course = Course(
            title = createCourseRequest.title,
            description = createCourseRequest.description,
            price = createCourseRequest.price,
            createdBy = userId
        )
        return TableCourses().insertCourse(course)
    }

    fun getAllCourses(getCourseRequest: GetCourseRequest): GetCoursesResponse {
        return TableCourses().getAllCourses(getCourseRequest)
    }

}