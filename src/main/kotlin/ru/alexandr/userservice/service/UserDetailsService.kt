package ru.alexandr.userservice.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import ru.alexandr.userservice.config.security.CustomUserDetails
import ru.alexandr.userservice.repository.UserRepository

@Service
class UserDetailsService(
    private val repository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails {
        val user = repository.findByEmail(email)
            ?: throw UsernameNotFoundException("User not found")

        return CustomUserDetails(
            email = user.email,
            password = user.userPassword,
        )
    }
}