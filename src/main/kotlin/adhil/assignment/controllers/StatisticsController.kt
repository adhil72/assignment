package adhil.assignment.controllers

import adhil.assignment.config.AppConfig
import adhil.assignment.dtos.GetStatisticsRequest
import adhil.assignment.security.permissive
import adhil.assignment.services.StatisticsService
import adhil.assignment.utils.timestamp
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("${AppConfig.BASE_PATH}/statistics")
class StatisticsController {
    @GetMapping("/get")
    fun getStatistics(request:HttpServletRequest, @RequestParam startDate:String, @RequestParam endDate:String): Any {
        permissive(listOf("admin","creator","customer"),request)
        return StatisticsService().getStatistics(request, GetStatisticsRequest(
            startDate = timestamp(startDate).toString(),
            endDate = timestamp(endDate).toString()
        ))
    }

}