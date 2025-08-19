package com.eklinik.e_klinikappnew.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session_prefs")

@Singleton
class SessionManager @Inject constructor(@ApplicationContext context: Context) {

    private val dataStore = context.dataStore

    companion object {
        val AUTH_TOKEN_KEY = stringPreferencesKey("auth_token")
        val ONBOARDING_COMPLETED_KEY = booleanPreferencesKey("onboarding_completed")
    }

    suspend fun saveAuthToken(token: String) {
        dataStore.edit { it[AUTH_TOKEN_KEY] = token }
    }

    fun readAuthToken(): Flow<String?> = dataStore.data.map { it[AUTH_TOKEN_KEY] }

    suspend fun clearAuthToken() {
        dataStore.edit { it.remove(AUTH_TOKEN_KEY) }
    }

    suspend fun saveOnboardingState(isCompleted: Boolean) {
        dataStore.edit { it[ONBOARDING_COMPLETED_KEY] = isCompleted }
    }

    fun readOnboardingState(): Flow<Boolean> = dataStore.data.map { it[ONBOARDING_COMPLETED_KEY] ?: false }
}