package com.eklinik.e_klinikappnew.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.eklinik.e_klinikappnew.auth.AuthViewModel

@Composable
fun RootNavGraph(navController: NavHostController) {
    val authViewModel: AuthViewModel = hiltViewModel()
    val isLoggedIn = authViewModel.isLoggedIn.collectAsState().value

    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) Screen.Home.route else Screen.Auth.route,
        route = Screen.Root.route
    ) {
        authNavGraph(navController = navController)
        homeNavGraph(navController = navController)
    }
}