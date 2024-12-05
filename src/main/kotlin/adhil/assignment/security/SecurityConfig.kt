package adhil.assignment.security

import adhil.assignment.config.AppConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher


@Configuration
open class SecurityConfig {
    @Bean
    @Throws(Exception::class)
    open fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf().disable().authorizeHttpRequests { auth ->
                auth.requestMatchers(AntPathRequestMatcher("${AppConfig.BASE_PATH}/**")).permitAll()
                .anyRequest().authenticated()
            }.httpBasic()
        return http.build()
    }
}
