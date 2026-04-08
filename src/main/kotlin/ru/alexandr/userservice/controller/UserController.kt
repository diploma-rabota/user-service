package ru.alexandr.userservice.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.alexandr.userservice.dto.UserResponse
import ru.alexandr.userservice.service.UserService

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService
) {

    @GetMapping("/{id}")
    fun getById(
        @PathVariable id: Long
    ): UserResponse {
        return userService.getById(id)
    }
}