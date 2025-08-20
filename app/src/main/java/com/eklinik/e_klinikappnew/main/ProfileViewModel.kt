package com.eklinik.e_klinikappnew.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eklinik.e_klinikappnew.data.models.UserResponse
import com.eklinik.e_klinikappnew.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ProfileUiState {
    object Loading : ProfileUiState
    data class Success(val userProfile: UserResponse) : ProfileUiState
    data class Error(val message: String) : ProfileUiState
}

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadProfile()
    }

    fun loadProfile() {
        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading
            try {
                val response = apiService.getMyProfile()
                if (response.isSuccessful) {
                    response.body()?.let { userProfile ->
                        _uiState.value = ProfileUiState.Success(userProfile)
                    } ?: run {
                        _uiState.value = ProfileUiState.Error("Profil bilgileri alınamadı")
                    }
                } else {
                    _uiState.value = ProfileUiState.Error("Sunucu hatası: ${response.code()}")
                }
            } catch (e: Exception) {
                _uiState.value = ProfileUiState.Error("İnternet bağlantınızı kontrol edin")
            }
        }
    }

    fun refreshProfile() {
        loadProfile()
    }
}