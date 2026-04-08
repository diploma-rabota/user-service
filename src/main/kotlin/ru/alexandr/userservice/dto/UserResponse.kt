package ru.alexandr.userservice.dto

data class UserResponse(
    val id: Long,
    val userName: String,
    val email: String,
)