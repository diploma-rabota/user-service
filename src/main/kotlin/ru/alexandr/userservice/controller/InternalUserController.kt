package ru.alexandr.userservice.controller.internal

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.alexandr.userservice.service.UserService

@RestController
@RequestMapping("/internal/users")
class InternalUserController(
    private val userService: UserService,
) {

    @GetMapping("/{userId}/email")
    fun getUserEmail(@PathVariable userId: Long): ResponseEntity<UserEmailResponse> {
        return ResponseEntity.ok(userService.getById(userId))
    }
}



data class UserEmailResponse(
    val userId: Long?,
    val email: String?
)