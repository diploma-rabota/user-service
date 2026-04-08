package ru.alexandr.userservice.exception


class UserNotFoundException(
    message: String
) : RuntimeException(message)