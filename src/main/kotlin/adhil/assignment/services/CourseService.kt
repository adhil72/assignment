package adhil.assignment.services

import adhil.assignment.dtos.CreateCourseRequest
import adhil.assignment.dtos.CreateCourseResponse
import adhil.assignment.dtos.GetCourseRequest
import adhil.assignment.dtos.GetCoursesResponse
import adhil.assignment.modals.Course
import adhil.assignment.tables.TableCourses

class CourseService {
    fun createCourse(createCourseRequest: CreateCourseRequest, createdBy:String):CreateCourseResponse {
        return TableCourses().insertCourse(Course(
            title = createCourseRequest.title,
            description = createCourseRequest.description,
            price = createCourseRequest.price,
            createdBy = createdBy
        ))
    }

    fun getAllCourses(getCourseRequest: GetCourseRequest): GetCoursesResponse {
        return TableCourses().getAllCourses(getCourseRequest)
    }

}