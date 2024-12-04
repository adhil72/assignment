package adhil.assignment.config

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan


@SpringBootApplication
@ComponentScan("adhil.assignment.controllers")
@ComponentScan("adhil.assignment.security")
open class AppConfig{
    companion object{
        const val BASE_URL = "http://localhost:3000"
        const val version = "v1"
        const val BASE_PATH = "/api/$version"
    }
}