package com.eklinik.e_klinikappnew.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.eklinik.e_klinikappnew.auth.LoginScreen
import com.eklinik.e_klinikappnew.auth.RegisterScreen
import com.eklinik.e_klinikappnew.auth.WelcomeScreen

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        startDestination = Screen.Welcome.route,
        route = Screen.Auth.route
    ) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(navController = navController)
        }
        composable(Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(Screen.Register.route) {
            RegisterScreen(navController = navController)
        }
    }
}