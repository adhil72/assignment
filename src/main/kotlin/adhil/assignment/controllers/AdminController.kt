package adhil.assignment.controllers

import adhil.assignment.config.AppConfig
import adhil.assignment.dtos.GetUsersResponse
import adhil.assignment.security.permissive
import adhil.assignment.services.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("${AppConfig.BASE_PATH}/admin")
class AdminController {
    @GetMapping("/users")
    fun getUsers(request: HttpServletRequest, @RequestParam(required = false) page:Int = 1, @RequestParam(required = false) limit:Int = 10, @RequestParam(required = false) search:String?): GetUsersResponse {
        permissive(listOf("admin"),request)
        return UserService().getUsers(page, limit, search)
    }
}