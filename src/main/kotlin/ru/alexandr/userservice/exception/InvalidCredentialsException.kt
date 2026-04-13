package ru.alexandr.userservice.exception

class InvalidCredentialsException(
    message: String = "Invalid email or password"
) : RuntimeException(message)