package adhil.assignment.controllers

import adhil.assignment.config.AppConfig
import adhil.assignment.dtos.CreateCourseRequest
import adhil.assignment.security.permissive
import adhil.assignment.services.CourseService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import javax.servlet.http.HttpServletRequest

@RequestMapping("${AppConfig.BASE_PATH}/creator")
class CreatorController {
    @PostMapping("/course")
    fun createCourse(request: HttpServletRequest, @RequestBody createCourseRequest: CreateCourseRequest) {
        permissive(listOf("creator"), request)
        CourseService().createCourse(request, createCourseRequest)
    }

    @GetMapping("/course")
    fun getCourses(request: HttpServletRequest, @RequestParam limit: Int, @RequestParam page: Int) {
        permissive(listOf("creator"), request)
        CourseService().getCreatorCourses(request, limit, page)
    }
}