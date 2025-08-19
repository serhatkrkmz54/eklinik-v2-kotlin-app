package com.eklinik.e_klinikappnew.auth

import android.os.Message
import android.util.Base64
import com.eklinik.e_klinikappnew.data.SessionManager
import com.eklinik.e_klinikappnew.data.models.LoginRequest
import com.eklinik.e_klinikappnew.network.ApiService
import kotlinx.coroutines.flow.Flow
import org.json.JSONObject
import javax.inject.Inject

sealed class AuthResult {
    object Success: AuthResult()
    data class Error(val message: String): AuthResult()
}

class AuthRepository @Inject constructor(
    private val apiService: ApiService,
    private val sessionManager: SessionManager
) {
    val authToken: Flow<String?> = sessionManager.readAuthToken()
    suspend fun login(loginRequest: LoginRequest): AuthResult {
        return try {
            val response = apiService.login(loginRequest)
            val token = response.accessToken
            val decodedRole = decodeJwtAndGetRole(token)
            if (decodedRole == "ROLE_PATIENT") {
                sessionManager.saveAuthToken(token)
                AuthResult.Success
            }else{
                AuthResult.Error("Bu uygulamaya sadece hastalar giriş yapabilir.Yöneticiniz ile iletişime geçiniz.")
            }
        }catch (e: Exception) {
            AuthResult.Error("T.C Kimlik No veya şifre hatalı.")
        }
    }

    private fun decodeJwtAndGetRole(token:String):String? {
        return try {
            val parts = token.split(".")
            if (parts.size < 2) return null
            val payload = String(Base64.decode(parts[1], Base64.URL_SAFE), Charsets.UTF_8)
            val jsonObject = JSONObject(payload)
            jsonObject.getString("role")
        }catch (e: Exception) {
            null
        }
    }
    suspend fun logout() {
        sessionManager.clearAuthToken()
    }
}