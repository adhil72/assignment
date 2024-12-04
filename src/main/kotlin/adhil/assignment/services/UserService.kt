package adhil.assignment.services

import adhil.assignment.config.AppConfig
import adhil.assignment.dtos.*
import adhil.assignment.exceptions.EmailNotVerifiedException
import adhil.assignment.exceptions.InvalidCredentialsException
import adhil.assignment.exceptions.InvalidVerificationLinkException
import adhil.assignment.exceptions.UserAlreadyExistsException
import adhil.assignment.modals.User
import adhil.assignment.tables.TableAccessToken
import adhil.assignment.tables.TableUser
import adhil.assignment.tables.TableVerification
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.UUID

class UserService {

    private val passwordEncoder = BCryptPasswordEncoder()
    val userTable = TableUser()

    fun signup(signupRequest: SignUpRequest): SignUpResponse {
        if (userTable.exists(signupRequest.email)) throw UserAlreadyExistsException("User with email ${signupRequest.email} already exists.")

        val user = User(
            email = signupRequest.email,
            password = passwordEncoder.encode(signupRequest.password),
            role = signupRequest.role,
            id = UUID.randomUUID().toString()
        )
        userTable.insertUser(user)

        val verify = TableVerification().createVerification(signupRequest.email)
        println("Email verification link: ${AppConfig.BASE_URL}${AppConfig.BASE_PATH}/user/verify?id=${verify.id}&email=${verify.email}")
        return SignUpResponse()
    }

    fun resendVerification(email: String): ResendVerificationResponse {
        if (!userTable.exists(email)) throw UserAlreadyExistsException("User with email $email does not exist.")
        val verify = TableVerification().createVerification(email)
        println("Email verification link: ${AppConfig.BASE_URL}${AppConfig.BASE_PATH}/user/verify?id=${verify.id}&email=${verify.email}")
        return ResendVerificationResponse()
    }

    fun verifyEmail(id: String, email: String): VerifyResponse {
        if (!TableVerification().exists(id, email)) throw InvalidVerificationLinkException("Invalid verification link.")
        if (TableVerification().isExpired(id, email)) throw InvalidVerificationLinkException("Verification link expired.")
        userTable.verifyEmail(email)
        return VerifyResponse()
    }

    fun signin(signInRequest: SigninRequest):SigninResponse{
        val user = userTable.getUserByEmail(signInRequest.email) ?: throw InvalidCredentialsException("Invalid email or password.")
        if (!user.verified) throw EmailNotVerifiedException("Email is not verified.")
        if (!passwordEncoder.matches(signInRequest.password, user.password)) throw InvalidCredentialsException("Invalid email or password.")
        val token = TableAccessToken().createAccessToken(user.id)
        return SigninResponse(token = token)
    }
}

