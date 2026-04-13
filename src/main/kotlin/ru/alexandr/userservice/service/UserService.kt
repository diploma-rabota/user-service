package ru.alexandr.userservice.service


import org.springframework.stereotype.Service
import ru.alexandr.userservice.controller.internal.UserEmailResponse
import ru.alexandr.userservice.repository.UserRepository


@Service
class UserService(
    private val userRepository: UserRepository,
) {

    fun getById(userId: Long): UserEmailResponse {
        val user = userRepository.findById(userId).orElse(null)

        return if (user != null) {
            UserEmailResponse(
                userId = user.id,
                email = user.email
            )
        } else {
            UserEmailResponse(
                userId = null,
                email = null
            )
        }
    }
}