package ru.alexandr.userservice.service

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.alexandr.userservice.config.security.CustomUserDetails
import ru.alexandr.userservice.controller.JwtResponse
import ru.alexandr.userservice.controller.LoginRequest
import ru.alexandr.userservice.controller.RegistrationRequest
import ru.alexandr.userservice.entity.User
import ru.alexandr.userservice.entity.toCustomUserDetails
import ru.alexandr.userservice.exception.InvalidCredentialsException
import ru.alexandr.userservice.exception.UserAlreadyExistsException
import ru.alexandr.userservice.metrics.UserMetrics
import ru.alexandr.userservice.repository.UserRepository
import ru.alexandr.userservice.util.jwt.JwtUtil

@Service
class RegistrationService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtil: JwtUtil,
    private val authenticationManager: AuthenticationManager,
    private val userMetrics: UserMetrics,
) {

    fun register(request: RegistrationRequest): JwtResponse {
        return try {
            userMetrics.registerTimer.recordCallable<JwtResponse> {
                userMetrics.registerPasswordLength.record(request.password.length.toDouble())

                val existingUser = userRepository.findByEmail(request.email)
                if (existingUser != null) {
                    userMetrics.registerDuplicateEmail.increment()
                    throw UserAlreadyExistsException("User with email ${request.email} already exists")
                }

                val user = User(
                    email = request.email,
                    userName = request.username,
                    userPassword = passwordEncoder.encode(request.password).toString(),
                    address = request.address,
                )

                val savedUser = userRepository.save(user)
                val token = jwtUtil.generateToken(savedUser.toCustomUserDetails())

                userMetrics.registerSuccess.increment()
                JwtResponse(token)
            }
        } catch (ex: Exception) {
            userMetrics.registerErrors.increment()
            throw ex
        }
    }

    fun login(request: LoginRequest): JwtResponse {
        return try {
            userMetrics.loginTimer.recordCallable<JwtResponse> {
                try {
                    val auth = authenticationManager.authenticate(
                        UsernamePasswordAuthenticationToken(request.email, request.password)
                    )

                    val user = auth.principal as CustomUserDetails
                    val token = jwtUtil.generateToken(user)

                    userMetrics.loginSuccess.increment()
                    JwtResponse(token)
                } catch (ex: BadCredentialsException) {
                    throw InvalidCredentialsException("Invalid email or password")
                }
            }
        } catch (ex: Exception) {
            userMetrics.loginErrors.increment()
            throw ex
        }
    }
}