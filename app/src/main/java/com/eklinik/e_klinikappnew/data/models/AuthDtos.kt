package com.eklinik.e_klinikappnew.data.models

data class ErrorResponse(
    val message: String
)
data class LoginRequest(
    val nationalId: String,
    val password: String
)
data class LoginResponse(
    val accessToken: String,
    val tokenType: String,
    val role: String?
)

data class UserResponse(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String
)