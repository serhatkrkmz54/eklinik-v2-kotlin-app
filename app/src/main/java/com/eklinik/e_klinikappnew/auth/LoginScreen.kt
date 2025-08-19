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
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.eklinik.e_klinikappnew.navigation.Screen
import com.eklinik.e_klinikappnew.ui.theme.AppGreen
import com.eklinik.e_klinikappnew.ui.theme.EKlinikAppNewTheme
import com.eklinik.e_klinikappnew.utils.PopupUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    val viewModel: AuthViewModel = hiltViewModel()
    val context = LocalContext.current
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
            snackbarHostState.showSnackbar(
                message = currentState.message,
                duration = SnackbarDuration.Short
            )
            viewModel.consumeState()
        }
        if (currentState is LoginUiState.Success) {
            // Popup göster
            PopupUtils.showLoginSuccessPopup(context)
            // State'i temizle (navigation RootNavGraph tarafından handle edilecek)
            viewModel.consumeState()
        }
    }

    Scaffold(
        modifier = Modifier.imePadding(),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { snackbarData ->
                val isError = loginState is LoginUiState.Error
                AppSnackbar(snackbarData = snackbarData, isError = isError)
            }
        },
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
            Spacer(modifier = Modifier.weight(0.3f))

            OutlinedTextField(
                value = nationalId,
                onValueChange = { viewModel.onNationalIdChanged(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 56.dp, max = 120.dp),
                label = { Text("T.C. Kimlik Numarası") },
                leadingIcon = { 
                    Icon(
                        imageVector = Icons.Default.AccountCircle, 
                        contentDescription = null,
                        tint = AppGreen,
                        modifier = Modifier.size(24.dp)
                    ) 
                },
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
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color(0xFFE5E7EB),
                    focusedBorderColor = AppGreen,
                    unfocusedContainerColor = Color(0xFFF9FAFB),
                    focusedContainerColor = Color(0xFFF9FAFB)
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { viewModel.onPasswordChanged(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 56.dp, max = 120.dp),
                label = { Text("Şifre") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock, 
                        contentDescription = null,
                        tint = AppGreen,
                        modifier = Modifier.size(24.dp)
                    ) 
                },
                trailingIcon = {
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        val icon = if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility
                        Icon(
                            imageVector = icon, 
                            contentDescription = "Şifre görünürlüğünü değiştir",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                shape = RoundedCornerShape(16),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color(0xFFE5E7EB),
                    focusedBorderColor = AppGreen,
                    unfocusedContainerColor = Color(0xFFF9FAFB),
                    focusedContainerColor = Color(0xFFF9FAFB)
                )
            )

            TextButton(
                onClick = { /* TODO: Şifremi Unuttum Ekranına Yönlendir */ },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Şifremi Unuttum?")
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 4. Giriş butonu onClick olayı ViewModel'e bağlandı.
            Button(
                onClick = { viewModel.login() },
                modifier = Modifier
                    .fillMaxWidth(),
                enabled = isFormValid && loginState !is LoginUiState.Loading && loginState !is LoginUiState.Success,
                shape = RoundedCornerShape(16)
            ) {
                if (loginState is LoginUiState.Loading || loginState is LoginUiState.Success) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(24.dp))
                } else {
                    Text(text = "Giriş Yap", style = MaterialTheme.typography.labelLarge)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // 5. "Sign Up" linki eklendi.
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 32.dp)
            ) {
                Text("Hesabınız yok mu?")
                TextButton(onClick = { navController.navigate(Screen.Register.route) }) {
                    Text("Kayıt Olun!")
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

@Composable
fun AppSnackbar(
    snackbarData: SnackbarData,
    isError: Boolean,
    modifier: Modifier = Modifier
) {
    // Duruma göre renk ve ikon seçimi
    val backgroundColor = if (isError) MaterialTheme.colorScheme.errorContainer else Color(0xFFD4EDDA) // Başarı için açık yeşil
    val contentColor = if (isError) MaterialTheme.colorScheme.onErrorContainer else Color(0xFF155724) // Başarı için koyu yeşil
    val icon = if (isError) Icons.Default.Warning else Icons.Default.CheckCircle

    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "Snackbar İkonu",
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = snackbarData.visuals.message,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}