package com.eklinik.e_klinikappnew.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.eklinik.e_klinikappnew.auth.AuthViewModel
import com.eklinik.e_klinikappnew.data.SessionManager
import com.eklinik.e_klinikappnew.onboarding.OnboardingScreen

@Composable
fun RootNavGraph(navController: NavHostController) {
    val authViewModel: AuthViewModel = hiltViewModel()
    val isLoggedIn by authViewModel.isLoggedIn.collectAsState()
    val onboardingCompleted by authViewModel.onboardingCompleted.collectAsState()
    val loginUiState by authViewModel.loginUiState.collectAsState()

    // Başlangıç rotası belirleme
    val startDestination = when {
        isLoggedIn -> Screen.Home.route
        onboardingCompleted -> Screen.Auth.route
        else -> Screen.Onboarding.route
    }

    // Login success durumunda navigation handle et
    LaunchedEffect(loginUiState) {
        if (loginUiState is com.eklinik.e_klinikappnew.auth.LoginUiState.Success) {
            val currentRoute = navController.currentDestination?.route
            val currentParentRoute = navController.currentDestination?.parent?.route
            
            // Sadece Auth ekranlarındayken navigate et
            if (currentParentRoute == Screen.Auth.route || currentRoute == Screen.Onboarding.route) {
                // Delay LoginScreen'de yapılıyor, burada hemen navigate et
                navController.navigate(Screen.Home.route) {
                    popUpTo(0) { inclusive = true }
                    launchSingleTop = true
                }
            }
        }
    }
    
    // Diğer auth durumu değişikliklerini handle et
    LaunchedEffect(isLoggedIn, onboardingCompleted) {
        val currentRoute = navController.currentDestination?.route
        val currentParentRoute = navController.currentDestination?.parent?.route

        when {
            // Kullanıcı çıkış yaptı ve Home ekranlarında
            !isLoggedIn && currentParentRoute == Screen.Home.route -> {
                navController.navigate(Screen.Auth.route) {
                    popUpTo(0) { inclusive = true }
                    launchSingleTop = true
                }
            }
            // Onboarding tamamlandı ama kullanıcı giriş yapmamış ve onboarding ekranında
            !isLoggedIn && onboardingCompleted && currentRoute == Screen.Onboarding.route -> {
                navController.navigate(Screen.Auth.route) {
                    popUpTo(Screen.Onboarding.route) { inclusive = true }
                    launchSingleTop = true
                }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        route = Screen.Root.route
    ) {
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onFinish = {
                    authViewModel.completeOnboarding()
                }
            )
        }
        
        authNavGraph(navController = navController)
        
        homeNavGraph(navController = navController)
    }
}