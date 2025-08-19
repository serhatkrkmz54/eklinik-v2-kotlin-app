package com.eklinik.e_klinikappnew.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.eklinik.e_klinikappnew.SplashScreen
import com.eklinik.e_klinikappnew.auth.AuthViewModel
import com.eklinik.e_klinikappnew.data.OnBoardingDataStore
import com.eklinik.e_klinikappnew.onboarding.OnboardingScreen
import kotlinx.coroutines.launch

@Composable
fun SetupNavGraph(navController: NavHostController) {
    // DataStore ve Scope'a burada ihtiyacımız var
    val context = LocalContext.current
    val dataStore = OnBoardingDataStore(context)
    val scope = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            val authViewModel: AuthViewModel = hiltViewModel()
            SplashScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable(route = Screen.Onboarding.route) {
            OnboardingScreen(
                onFinish = {
                    scope.launch {
                        dataStore.saveOnboardingState(isCompleted = true)
                    }

                    // Ardından Auth dünyasına yönlendiriyoruz.
                    navController.navigate(Screen.Auth.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        // Auth ve Home grafları olduğu gibi kalıyor
        authNavGraph(navController = navController)
        homeNavGraph(navController = navController)
    }
}