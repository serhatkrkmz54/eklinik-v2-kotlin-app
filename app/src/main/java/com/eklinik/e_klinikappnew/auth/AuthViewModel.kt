package com.eklinik.e_klinikappnew.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eklinik.e_klinikappnew.data.SessionManager
import com.eklinik.e_klinikappnew.data.models.LoginRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface LoginUiState {
    object Idle : LoginUiState
    object Loading : LoginUiState
    data class Success(val message: String) : LoginUiState
    data class Error(val message: String) : LoginUiState
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repo: AuthRepository,
    private val sessionManager: SessionManager, // Onboarding için de kullanacağız
    @ApplicationContext private val context: Context
) : ViewModel() {

    val isLoggedIn = repo.authToken
        .map { it != null }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val onboardingCompleted = sessionManager.readOnboardingState()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true) // Başlangıçta tamamlanmış varsay, anlık kontrol edilecek

    private val _loginUiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val loginUiState = _loginUiState.asStateFlow()

    private val _nationalId = MutableStateFlow("")
    val nationalId = _nationalId.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    fun onNationalIdChanged(value: String) {
        if (value.all { it.isDigit() } && value.length <= 11) {
            _nationalId.value = value
        }
    }

    fun onPasswordChanged(value: String) {
        _password.value = value
    }

    fun login() {
        viewModelScope.launch {
            _loginUiState.value = LoginUiState.Loading
            val result = repo.login(LoginRequest(nationalId = _nationalId.value, password = _password.value))
            when (result) {
                is AuthResult.Success -> {
                    _loginUiState.value = LoginUiState.Success("Giriş başarılı!")
                }
                is AuthResult.Error -> _loginUiState.value = LoginUiState.Error(result.message)
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            repo.logout()
        }
    }

    fun consumeState() {
        _loginUiState.value = LoginUiState.Idle
    }

    fun completeOnboarding() {
        viewModelScope.launch {
            sessionManager.saveOnboardingState(true)
        }
    }
}