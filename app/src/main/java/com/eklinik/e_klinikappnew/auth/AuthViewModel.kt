package com.eklinik.e_klinikappnew.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eklinik.e_klinikappnew.data.models.LoginRequest
import dagger.hilt.android.lifecycle.HiltViewModel
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
    data class Error(val message: String) : LoginUiState
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repo: AuthRepository
): ViewModel() {
    val isLoggedIn = repo.authToken
        .map { token -> token != null }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    // Login ekranının anlık durumunu tutacak olan StateFlow
    private val _loginUiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val loginUiState: StateFlow<LoginUiState> = _loginUiState.asStateFlow()

    // Input alanlarının değerlerini ViewModel'de tutuyoruz
    private val _nationalId = MutableStateFlow("")
    val nationalId: StateFlow<String> = _nationalId.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    fun onNationalIdChanged(value: String) {
        if (value.all { char -> char.isDigit() } && value.length <= 11) {
            _nationalId.value = value
        }
    }

    fun onPasswordChanged(value: String) {
        _password.value = value
    }

    fun login() {
        viewModelScope.launch {
            _loginUiState.value = LoginUiState.Loading

            val loginRequest = LoginRequest(
                nationalId = _nationalId.value,
                password = _password.value
            )

            val result = repo.login(loginRequest)

            when(result) {
                is AuthResult.Success -> {
                    _loginUiState.value = LoginUiState.Idle
                }
                is AuthResult.Error -> {
                    _loginUiState.value = LoginUiState.Error(result.message)
                }
            }
        }
    }
    fun consumeError() {
        _loginUiState.value = LoginUiState.Idle
    }
}