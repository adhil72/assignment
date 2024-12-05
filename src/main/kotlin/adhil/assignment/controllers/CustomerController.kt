package adhil.assignment.controllers

import adhil.assignment.config.AppConfig
import adhil.assignment.dtos.GetCourseRequest
import adhil.assignment.dtos.GetCoursesResponse
import adhil.assignment.security.permissive
import adhil.assignment.services.CourseService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("${AppConfig.BASE_PATH}/customer")
class CustomerController {
    @GetMapping("/course")
    fun getCourse(
        request: HttpServletRequest,
        @RequestParam("page", required = false, defaultValue = "1") page: Int,
        @RequestParam("limit", required = false, defaultValue = "10") limit: Int,
        @RequestParam("search", required = false) search: String?
    ): GetCoursesResponse {
        permissive(listOf("customer"), request)
        return CourseService().getAllCourses(GetCourseRequest(page, limit, search))
    }
}