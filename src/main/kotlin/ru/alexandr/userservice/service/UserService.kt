package ru.alexandr.userservice.service


import org.springframework.stereotype.Service
import ru.alexandr.userservice.dto.UserResponse
import ru.alexandr.userservice.exception.UserNotFoundException
import ru.alexandr.userservice.repository.UserRepository


@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun getById(id: Long): UserResponse {
        val user = userRepository.findById(id).orElseThrow {
            UserNotFoundException("Пользователь с id=$id не найден")
        }

        return UserResponse(
            id = requireNotNull(user.id),
            userName = user.userName,
            email = user.email,
        )
    }
}