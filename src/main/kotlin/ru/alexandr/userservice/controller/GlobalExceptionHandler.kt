package ru.alexandr.userservice.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.alexandr.userservice.exception.UserNotFoundException

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFound(ex: UserNotFoundException): ResponseEntity<Map<String, Any>> {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(
                mapOf(
                    "status" to HttpStatus.NOT_FOUND.value(),
                    "message" to (ex.message ?: "Пользователь не найден")
                )
            )
    }
}