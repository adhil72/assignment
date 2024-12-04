package adhil.assignment.security

import adhil.assignment.exceptions.PermissionDeniedException
import javax.servlet.http.HttpServletRequest

fun permissive(roles:List<String>,request: HttpServletRequest){
    val role = request.getAttribute("role") as String
    if(role !in roles){
        throw PermissionDeniedException("Permission Denied")
    }
}
