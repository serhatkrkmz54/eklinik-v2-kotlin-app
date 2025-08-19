package com.eklinik.e_klinikappnew

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.eklinik.e_klinikappnew.auth.AuthViewModel
import com.eklinik.e_klinikappnew.data.OnBoardingDataStore
import com.eklinik.e_klinikappnew.navigation.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first

@Composable
fun SplashScreen(navController: NavController, authViewModel: AuthViewModel) {
    val context = LocalContext.current
    val dataStore = OnBoardingDataStore(context)
    val isLoggedIn by authViewModel.isLoggedIn.collectAsState()

    LaunchedEffect(key1 = true) {
        val isOnBoardingCompleted = dataStore.readOnboardingState().first()

        delay(2500L)
        val route = if (isLoggedIn) {
            Screen.Home.route
        } else if (isOnBoardingCompleted) {
            Screen.Auth.route
        } else {
            Screen.Onboarding.route
        }

        navController.navigate(route) {
            popUpTo(Screen.Splash.route) { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.medics_logo),
                contentDescription = "E-KLİNİK"
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "E-KLINIK",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White
            )
        }

        DotSpinner(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 90.dp)
        )
    }
}