package com.eklinik.e_klinikappnew.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.eklinik.e_klinikappnew.R
import com.eklinik.e_klinikappnew.navigation.Screen
import com.eklinik.e_klinikappnew.ui.theme.AppGreen
import com.eklinik.e_klinikappnew.ui.theme.EKlinikAppNewTheme
import com.eklinik.e_klinikappnew.ui.theme.TextPrimary
import com.eklinik.e_klinikappnew.ui.theme.TextSecondary
@Composable
fun WelcomeScreen(navController: NavController) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(id = R.drawable.medics_logo_welcome),
                contentDescription = "EKlinik Logo Welcome",
                modifier = Modifier.size(66.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "E-Klinik", style = MaterialTheme.typography.titleLarge, color = AppGreen)

            Spacer(modifier = Modifier.height(48.dp))

            // Başlık ve Alt Başlık
            Text(
                text = "Randevu almaya başlayın!",
                style = MaterialTheme.typography.titleLarge,
                color = TextPrimary,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Hesabınız varsa giriş yapabilirsiniz, yoksa kayıt olup randevu almaya başlayabilirsiniz!",
                style = MaterialTheme.typography.bodyLarge,
                color = TextSecondary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.weight(0.7f))

            // Butonlar
            Button(
                onClick = { navController.navigate(Screen.Login.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(text = "Giriş Yap", style = MaterialTheme.typography.labelLarge)
            }
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(
                onClick = { navController.navigate(Screen.Register.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = "Kayıt Ol",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(45.dp))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    EKlinikAppNewTheme {
        WelcomeScreen(navController = rememberNavController())
    }
}