package com.eklinik.e_klinikappnew.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.eklinik.e_klinikappnew.navigation.Screen
import com.eklinik.e_klinikappnew.ui.theme.AppGreen
import com.eklinik.e_klinikappnew.ui.theme.EKlinikAppNewTheme
import com.eklinik.e_klinikappnew.ui.theme.TextPrimary
import com.eklinik.e_klinikappnew.ui.theme.TextSecondary
import com.eklinik.e_klinikappnew.ui.theme.UnfocusedBorderColor
import com.eklinik.e_klinikappnew.utils.PopupUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val nationalId by viewModel.registerNationalId.collectAsState()
    val firstName by viewModel.firstName.collectAsState()
    val lastName by viewModel.lastName.collectAsState()
    val email by viewModel.email.collectAsState()
    val phoneNumber by viewModel.phoneNumber.collectAsState()
    val password by viewModel.registerPassword.collectAsState()
    val termsAccepted by viewModel.termsAccepted.collectAsState()
    val registerUiState by viewModel.registerUiState.collectAsState()

    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(registerUiState) {
        when (val state = registerUiState) {
            is RegisterUiState.Success -> {
                PopupUtils.showSuccessPopup(
                    context = context,
                    title = "Kayıt Başarılı!",
                    message = state.message,
                    buttonText = "Giriş Yap"
                ) {
                    viewModel.consumeRegisterState()
                    navController.navigate(Screen.Home.route) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            }

            is RegisterUiState.Error -> {
                PopupUtils.showErrorPopup(
                    context = context,
                    title = "Kayıt Hatası",
                    message = state.message
                ) {
                    viewModel.consumeRegisterState()
                }
            }

            else -> {}
        }
    }

    LaunchedEffect(Unit) {
        if (phoneNumber.isEmpty()) {
            viewModel.onPhoneNumberChanged("+90")
        }
    }

    Scaffold(
        modifier = Modifier.imePadding(),
        topBar = {
            TopAppBar(
                title = { Text("Kayıt Ol") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Geri"
                        )
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
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = nationalId,
                onValueChange = viewModel::onRegisterNationalIdChanged,
                label = { Text("TC Kimlik Numarası") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = null,
                        tint = AppGreen
                    )
                },
                trailingIcon = {
                    if (isNationalIdValid(nationalId)) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Valid",
                            tint = AppGreen
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp, max = 120.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = UnfocusedBorderColor,
                    focusedBorderColor = AppGreen
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = firstName,
                onValueChange = viewModel::onFirstNameChanged,
                label = { Text("Ad") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = AppGreen
                    )
                },
                modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp, max = 120.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = UnfocusedBorderColor,
                    focusedBorderColor = AppGreen
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = lastName,
                onValueChange = viewModel::onLastNameChanged,
                label = { Text("Soyad") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = AppGreen
                    )
                },
                modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp, max = 120.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = UnfocusedBorderColor,
                    focusedBorderColor = AppGreen
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = viewModel::onEmailChanged,
                label = { Text("E-posta") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = null,
                        tint = AppGreen
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp, max = 120.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = UnfocusedBorderColor,
                    focusedBorderColor = AppGreen
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = phoneNumber,
                onValueChange = viewModel::onPhoneNumberChanged,
                label = { Text("Telefon Numarası") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = null,
                        tint = AppGreen
                    )
                },
                trailingIcon = {
                    if (isPhoneNumberValid(phoneNumber)) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Valid",
                            tint = AppGreen
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp, max = 120.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = UnfocusedBorderColor,
                    focusedBorderColor = AppGreen
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = viewModel::onRegisterPasswordChanged,
                label = { Text("Şifre") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        tint = AppGreen
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (passwordVisible) "Şifreyi gizle" else "Şifreyi göster",
                            tint = AppGreen
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp, max = 120.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = UnfocusedBorderColor,
                    focusedBorderColor = AppGreen
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = termsAccepted,
                    onCheckedChange = viewModel::onTermsAcceptedChanged,
                    colors = CheckboxDefaults.colors(
                        checkedColor = AppGreen,
                        uncheckedColor = TextSecondary
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                val annotatedText = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = TextSecondary)) {
                        append("Sözleşme şartlarını ve KVKK politikalarını ")
                    }
                    pushStringAnnotation(
                        tag = "terms",
                        annotation = "terms_of_service"
                    )
                    withStyle(style = SpanStyle(color = AppGreen, fontWeight = FontWeight.Medium)) {
                        append("sayfadan")
                    }
                    pop()
                    withStyle(style = SpanStyle(color = TextSecondary)) {
                        append(" okudum ve onaylıyorum.")
                    }
                }
                ClickableText(
                    text = annotatedText,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 14.sp
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { offset ->
                        annotatedText.getStringAnnotations(
                            tag = "terms",
                            start = offset,
                            end = offset
                        ).firstOrNull()?.let {
                            navController.navigate(Screen.TermsOfService.route)
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    val validationError = getValidationError(
                        nationalId,
                        firstName,
                        lastName,
                        email,
                        phoneNumber,
                        password,
                        termsAccepted
                    )
                    if (validationError != null) {
                        PopupUtils.showErrorPopup(
                            context = context,
                            title = "Geçersiz Bilgi",
                            message = validationError
                        )
                    } else {
                        viewModel.register()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppGreen,
                    disabledContainerColor = AppGreen.copy(alpha = 0.6f)
                ),
                enabled = registerUiState !is RegisterUiState.Loading
            ) {
                if (registerUiState is RegisterUiState.Loading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                } else {
                    Text(
                        text = "Kaydı Tamamla",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Zaten hesabınız var mı?",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = TextSecondary
                    )
                )
                TextButton(
                    onClick = { navController.navigate(Screen.Login.route) },
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "Giriş Yap",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = AppGreen,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
        }
    }
}

private fun isFormValid(
    nationalId: String,
    firstName: String,
    lastName: String,
    email: String,
    phoneNumber: String,
    password: String,
    termsAccepted: Boolean
): Boolean {
    return isNationalIdValid(nationalId) &&
            isFirstNameValid(firstName) &&
            isLastNameValid(lastName) &&
            isEmailValid(email) &&
            isPhoneNumberValid(phoneNumber) &&
            isPasswordValid(password) &&
            termsAccepted
}

private fun isNationalIdValid(nationalId: String): Boolean {
    return nationalId.length == 11 && nationalId.all { it.isDigit() }
}

private fun isFirstNameValid(firstName: String): Boolean {
    return firstName.length >= 3 && firstName.isNotBlank()
}

private fun isLastNameValid(lastName: String): Boolean {
    return lastName.length >= 3 && lastName.isNotBlank()
}

private fun isEmailValid(email: String): Boolean {
    return email.isNotBlank() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

private fun isPhoneNumberValid(phoneNumber: String): Boolean {
    return phoneNumber.length == 13 && phoneNumber.startsWith("+90") &&
            phoneNumber.substring(3).all { it.isDigit() }
}

private fun isPasswordValid(password: String): Boolean {
    return password.length >= 6 && password.isNotBlank()
}

private fun getValidationError(
    nationalId: String,
    firstName: String,
    lastName: String,
    email: String,
    phoneNumber: String,
    password: String,
    termsAccepted: Boolean
): String? {
    return when {
        nationalId.isBlank() -> "TC Kimlik Numarası boş olamaz."
        nationalId.length != 11 -> "TC Kimlik Numarası 11 haneli olmalıdır."
        !nationalId.all { it.isDigit() } -> "TC Kimlik Numarası sadece rakam içermelidir."
        firstName.isBlank() -> "İsim boş olamaz."
        firstName.length < 3 -> "İsim en az 3 karakter olmalıdır."
        lastName.isBlank() -> "Soyisim boş olamaz."
        lastName.length < 3 -> "Soyisim en az 3 karakter olmalıdır."
        email.isBlank() -> "E-posta boş olamaz."
        !isEmailValid(email) -> "Geçerli bir e-posta adresi giriniz."
        phoneNumber.isBlank() -> "Telefon numarası boş olamaz."
        !phoneNumber.startsWith("+90") -> "Telefon numarası '+90' ile başlamalıdır."
        phoneNumber.length != 13 -> "Telefon numarası '+905xxxxxxxxx' formatında olmalıdır."
        !phoneNumber.substring(3).all { it.isDigit() } -> "Telefon numarası sadece rakam içermelidir."
        password.isBlank() -> "Şifre boş olamaz."
        password.length < 6 -> "Şifre en az 6 karakter olmalıdır."
        !termsAccepted -> "Kullanım şartlarını kabul etmelisiniz."
        else -> null
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    EKlinikAppNewTheme {
        RegisterScreen(navController = rememberNavController())
    }
}