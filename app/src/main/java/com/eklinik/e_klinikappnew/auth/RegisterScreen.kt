package com.eklinik.e_klinikappnew.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.eklinik.e_klinikappnew.ui.theme.EKlinikAppNewTheme

@Composable
fun RegisterScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Register Ekranı")
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    EKlinikAppNewTheme {
        RegisterScreen(navController = rememberNavController())
    }
}