package adhil.assignment

import adhil.assignment.config.AppConfig
import adhil.assignment.config.DbConfig
import org.springframework.boot.SpringApplication

fun main() {
    DbConfig.connect()
    SpringApplication.run(AppConfig::class.java)
}