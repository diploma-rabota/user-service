package ru.alexandr.userservice.service

import org.springframework.stereotype.Service
import ru.alexandr.userservice.controller.internal.UserEmailResponse
import ru.alexandr.userservice.metrics.UserMetrics
import ru.alexandr.userservice.repository.UserRepository

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userMetrics: UserMetrics,
) {

    fun getById(userId: Long): UserEmailResponse {
        return try {
            userMetrics.internalGetEmailTimer.recordCallable<UserEmailResponse> {
                val user = userRepository.findById(userId).orElse(null)

                if (user != null) {
                    userMetrics.internalGetEmailSuccess.increment()
                    UserEmailResponse(
                        userId = user.id,
                        email = user.email
                    )
                } else {
                    userMetrics.internalGetEmailNotFound.increment()
                    UserEmailResponse(
                        userId = null,
                        email = null
                    )
                }
            }!!
        } catch (ex: Exception) {
            userMetrics.internalGetEmailErrors.increment()
            throw ex
        }
    }
}