package com.eklinik.e_klinikappnew.network

import com.eklinik.e_klinikappnew.data.models.LoginRequest
import com.eklinik.e_klinikappnew.data.models.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}