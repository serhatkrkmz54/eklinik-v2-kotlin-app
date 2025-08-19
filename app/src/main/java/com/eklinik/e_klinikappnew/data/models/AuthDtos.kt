package com.eklinik.e_klinikappnew.data.models

data class LoginRequest(
    val nationalId: String,
    val password: String
)
data class LoginResponse(
    val accessToken: String,
    val tokenType: String,
    val role: String?
)

// LoginResponse içinde gelen kullanıcı bilgisi modeli
data class UserResponse(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String
)