package adhil.assignment.security

import adhil.assignment.tables.TableAccessToken
import adhil.assignment.tables.TableUser
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
open class AuthenticationFilter: OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (PublicRoutes().publicRoutes.contains(request.requestURI)) {
            filterChain.doFilter(request, response)
            return
        }

        var token = request.getHeader("Authorization")
        if (token != null && token.startsWith("Bearer ")) {
            try {
                token = token.substring(7)
                val uid = TableAccessToken().validateToken(token)
                request.setAttribute("uid", uid)
            } catch (e: Exception) {
                response.status = HttpServletResponse.SC_UNAUTHORIZED
                response.writer.write("Invalid or expired token.")
                return
            }
        } else {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            response.writer.write("Authorization token is missing.")
            return
        }
    }
}