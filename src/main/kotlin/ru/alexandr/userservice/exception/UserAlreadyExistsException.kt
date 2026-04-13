package ru.alexandr.userservice.exception

class UserAlreadyExistsException(
    message: String
) : RuntimeException(message)