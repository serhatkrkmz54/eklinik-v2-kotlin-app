package com.eklinik.e_klinikappnew.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.homeNavGraph(navController: NavHostController) {
    navigation(
        startDestination = Screen.Dashboard.route, // Başlangıç ekranı Dashboard
        route = Screen.Home.route
    ) {
        // Home (giriş yapılmış) dünyasında sadece tek bir ekran var: Dashboard.
        composable(Screen.Dashboard.route) {
            PlaceholderScreen(screenName = "Dashboard")
        }
    }
}

// Projenin derlenmesi için geçici yer tutucu ekran
@Composable
fun PlaceholderScreen(screenName: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "$screenName Ekranı")
    }
}