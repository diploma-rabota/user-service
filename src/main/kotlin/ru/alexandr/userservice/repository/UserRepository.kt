package ru.alexandr.userservice.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.alexandr.userservice.entity.User

interface UserRepository: JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
}