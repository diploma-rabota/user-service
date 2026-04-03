package ru.alexandr.orderservice.service
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.alexandr.orderservice.config.security.CustomUserDetails
import ru.alexandr.orderservice.controller.JwtResponse
import ru.alexandr.orderservice.controller.LoginRequest
import ru.alexandr.orderservice.controller.RegistrationRequest
import ru.alexandr.orderservice.entity.User
import ru.alexandr.orderservice.entity.toCustomUserDetails
import ru.alexandr.orderservice.repository.UserRepository
import ru.alexandr.orderservice.util.jwt.JwtUtil

@Service
class RegistrationService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtil: JwtUtil,
    private val authenticationManager: AuthenticationManager,
    ) {
    fun register(request: RegistrationRequest): JwtResponse {
        require(userRepository.findByEmail(request.email) == null)
        val user = User(
            email = request.email,
            userName = request.username,
            userPassword = passwordEncoder.encode(request.password).toString(),
            address = request.address,
        )

        val savedUser = userRepository.save(user)
        val token = jwtUtil.generateToken(savedUser.toCustomUserDetails())
        return JwtResponse(token)
    }


    fun login(request: LoginRequest): JwtResponse {
        val auth = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(request.email, request.password)
        )
        val user = auth.principal as CustomUserDetails

        val token = jwtUtil.generateToken(user)
        return JwtResponse(token)
    }

}