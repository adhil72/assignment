package adhil.assignment.controllers

import adhil.assignment.config.AppConfig
import adhil.assignment.dtos.*
import adhil.assignment.exceptions.InvalidCredentialsException
import adhil.assignment.exceptions.InvalidVerificationLinkException
import adhil.assignment.exceptions.UserAlreadyExistsException
import adhil.assignment.services.UserService
import adhil.assignment.tables.TableExceptions
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping(AppConfig.BASE_PATH + "/user")
class UserController {
    @PostMapping("/signup")
    fun signup(@RequestBody body:SignUpRequest): SignUpResponse {
        return UserService().signup(body)
    }

    @GetMapping("/verify")
    fun verifyEmail(@RequestParam("id") id:String, @RequestParam("email") email:String): VerifyResponse {
        return UserService().verifyEmail(id, email)
    }

    @PostMapping("/signin")
    fun signin(@RequestBody body:SigninRequest): SigninResponse {
        return UserService().signin(body)
    }

    @GetMapping("/resend-verification")
    fun resendVerification(@RequestParam("email") email:String): ResendVerificationResponse {
        return UserService().resendVerification(email)
    }
}