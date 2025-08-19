package com.eklinik.e_klinikappnew.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.eklinik.e_klinikappnew.auth.AuthViewModel
import com.eklinik.e_klinikappnew.ui.theme.EKlinikAppNewTheme
import com.eklinik.e_klinikappnew.utils.PopupUtils

@Composable
fun DashboardScreen(navController: NavController) {
    val authViewModel: AuthViewModel = hiltViewModel()
    val context = LocalContext.current
    // ViewModel'deki giriş durumunu dinliyoruz
    val isLoggedIn by authViewModel.isLoggedIn.collectAsState()

    // Navigation RootNavGraph tarafından handle ediliyor

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Dashboard Ekranı")
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = {
                PopupUtils.showLogoutSuccessPopup(context)
                authViewModel.logout()
            }) {
                Text(text = "Çıkış Yap")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    // Preview'ın hata vermemesi için navController ekliyoruz.
    EKlinikAppNewTheme {
        DashboardScreen(navController = rememberNavController())
    }
}