package adhil.assignment.security

import adhil.assignment.config.AppConfig
import org.springframework.security.web.util.matcher.RequestMatcher

class PublicRoutes {
    val publicRoutes = listOf(
        AppConfig.BASE_PATH + "/user/signin",
        AppConfig.BASE_PATH + "/user/signup",
        AppConfig.BASE_PATH + "/user/verify",
        AppConfig.BASE_PATH + "/user/resend-verification",
        AppConfig.BASE_PATH + "/order/payment/success",
        AppConfig.BASE_PATH + "/order/payment/failure"
    )

    fun isPublicRoute(path: String): Boolean {
        println(path)
        return publicRoutes.any { route -> path.startsWith(route) }
    }

}