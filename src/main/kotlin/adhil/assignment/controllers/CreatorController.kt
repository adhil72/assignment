package adhil.assignment.controllers

import adhil.assignment.config.AppConfig
import adhil.assignment.dtos.CreateCourseRequest
import adhil.assignment.dtos.CreateCourseResponse
import adhil.assignment.dtos.GetCoursesResponse
import adhil.assignment.security.permissive
import adhil.assignment.services.CourseService
import adhil.assignment.utils.validateBody
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("${AppConfig.BASE_PATH}/creator")
class CreatorController {
    @PostMapping("/course")
    fun createCourse(request: HttpServletRequest,@RequestBody createCourseRequest: CreateCourseRequest):CreateCourseResponse {
        permissive(listOf("creator"), request)
        return CourseService().createCourse(request, createCourseRequest)
    }

    @GetMapping("/course")
    fun getCourses(request: HttpServletRequest, @RequestParam(required = false) limit: Int = 10, @RequestParam(required = false) page: Int=1):GetCoursesResponse {
        permissive(listOf("creator"), request)
        return CourseService().getCreatorCourses(request, limit, page)
    }
}