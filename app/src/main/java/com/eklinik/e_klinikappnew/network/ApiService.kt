package com.eklinik.e_klinikappnew.network

import com.eklinik.e_klinikappnew.data.models.LoginRequest
import com.eklinik.e_klinikappnew.data.models.LoginResponse
import com.eklinik.e_klinikappnew.data.models.RegisterPatientCombinatedRequest
import com.eklinik.e_klinikappnew.data.models.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("/api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
    
    @POST("/api/auth/register")
    suspend fun register(@Body request: RegisterPatientCombinatedRequest): Response<LoginResponse>
    
    @GET("/api/auth/me")
    suspend fun getMyProfile(): Response<UserResponse>
}