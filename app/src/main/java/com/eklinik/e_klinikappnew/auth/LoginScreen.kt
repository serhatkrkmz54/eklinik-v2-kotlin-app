package com.eklinik.e_klinikappnew.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.eklinik.e_klinikappnew.navigation.Screen
import com.eklinik.e_klinikappnew.ui.theme.AppGreen
import com.eklinik.e_klinikappnew.ui.theme.EKlinikAppNewTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    val viewModel: AuthViewModel = hiltViewModel()
    val nationalId by viewModel.nationalId.collectAsState()
    val password by viewModel.password.collectAsState()
    val loginState by viewModel.loginUiState.collectAsState()
    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }
    val isNationalIdValid = nationalId.length == 11

    val isFormValid = isNationalIdValid && password.isNotBlank()

    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(loginState) {
        val currentState = loginState
        if (currentState is LoginUiState.Error) {
            snackbarHostState.showSnackbar(message = currentState.message)
            viewModel.consumeError()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Giriş Yap") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Geri")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(0.5f))

            OutlinedTextField(
                value = nationalId,
                onValueChange = { viewModel.onNationalIdChanged(it) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("T.C. Kimlik Numarası") },
                leadingIcon = { Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null) },
                trailingIcon = {
                    if (nationalId.isNotEmpty()) {
                        val icon = if (isNationalIdValid) Icons.Default.CheckCircle else Icons.Default.Clear
                        val tint = if (isNationalIdValid) AppGreen else MaterialTheme.colorScheme.error
                        Icon(imageVector = icon, contentDescription = "Doğrulama Durumu", tint = tint)
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                shape = RoundedCornerShape(16),
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { viewModel.onPasswordChanged(it) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Şifre") },
                // --- YENİ EKLENDİ: Soldaki ikon ---
                leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) },
                trailingIcon = {
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        val icon = if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility
                        Icon(imageVector = icon, contentDescription = "Şifre görünürlüğünü değiştir")
                    }
                },
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                shape = RoundedCornerShape(16)
            )

            TextButton(
                onClick = { /* TODO: Şifremi Unuttum Ekranına Yönlendir */ },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Forgot Password?")
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 4. Giriş butonu onClick olayı ViewModel'e bağlandı.
            Button(
                onClick = { viewModel.login() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = isFormValid && loginState !is LoginUiState.Loading,
                shape = RoundedCornerShape(16)
            ) {
                if (loginState is LoginUiState.Loading) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(24.dp))
                } else {
                    Text(text = "Login", style = MaterialTheme.typography.labelLarge)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // 5. "Sign Up" linki eklendi.
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 32.dp)
            ) {
                Text("Don't have an account?")
                TextButton(onClick = { navController.navigate(Screen.Register.route) }) {
                    Text("Sign Up")
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    EKlinikAppNewTheme {
        LoginScreen(navController = rememberNavController())
    }
}