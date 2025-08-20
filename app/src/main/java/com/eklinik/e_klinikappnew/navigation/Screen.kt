package com.eklinik.e_klinikappnew.navigation

sealed class Screen(val route: String) {
    object Root : Screen("root_graph")
    object Auth : Screen("auth_graph")
    object Home : Screen("home_graph")
    object Splash : Screen("splash_screen")
    object Onboarding : Screen("onboarding_screen")
    object Welcome : Screen("welcome_screen")
    object Login : Screen("login_screen")
    object Register : Screen("register_screen")
    object TermsOfService : Screen("terms_of_service_screen")
    object Dashboard : Screen("dashboard_screen")
    object Appointments : Screen("appointments_screen")
    object Doctors : Screen("doctors_screen")
    object Profile : Screen("profile_screen")
    object Settings : Screen("settings_screen")
}