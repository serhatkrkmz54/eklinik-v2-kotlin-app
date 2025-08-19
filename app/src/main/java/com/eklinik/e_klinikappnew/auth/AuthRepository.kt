package com.eklinik.e_klinikappnew.auth

import android.os.Message
import android.util.Base64
import com.eklinik.e_klinikappnew.data.SessionManager
import com.eklinik.e_klinikappnew.data.models.ErrorResponse
import com.eklinik.e_klinikappnew.data.models.LoginRequest
import com.eklinik.e_klinikappnew.network.ApiService
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import org.json.JSONObject
import javax.inject.Inject

sealed class AuthResult {
    object Success : AuthResult()
    data class Error(val message: String) : AuthResult()
}

class AuthRepository @Inject constructor(
    private val apiService: ApiService,
    private val sessionManager: SessionManager
) {
    val authToken: Flow<String?> = sessionManager.readAuthToken()

    suspend fun login(loginRequest: LoginRequest): AuthResult {
        return try {
            val response = apiService.login(loginRequest)
            if (response.isSuccessful) {
                val loginResponse = response.body()!!
                val token = loginResponse.accessToken
                val decodedRole = decodeJwtAndGetRole(token)

                // Sadece hasta rolüne izin ver (veya test için diğerleri)
                if (decodedRole == "ROLE_PATIENT" || decodedRole == "ROLE_ADMIN" || decodedRole == "ROLE_DOCTOR") {
                    sessionManager.saveAuthToken(token)
                    AuthResult.Success
                } else {
                    AuthResult.Error("Bu uygulamaya giriş yetkiniz bulunmamaktadır.")
                }
            } else {
                val errorJsonString = response.errorBody()?.string()
                val errorMessage = errorJsonString?.let {
                    Gson().fromJson(it, ErrorResponse::class.java).message
                } ?: "Bilinmeyen bir hata oluştu."
                AuthResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            AuthResult.Error("İnternet bağlantınızı kontrol edin veya sunucu yanıt vermiyor.")
        }
    }

    suspend fun logout() {
        sessionManager.clearAuthToken()
    }

    private fun decodeJwtAndGetRole(token: String): String? {
        return try {
            val parts = token.split(".")
            if (parts.size < 2) return null
            val payload = String(Base64.decode(parts[1], Base64.URL_SAFE), Charsets.UTF_8)
            JSONObject(payload).getString("role")
        } catch (e: Exception) {
            null
        }
    }
}