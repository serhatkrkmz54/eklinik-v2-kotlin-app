package com.eklinik.e_klinikappnew.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.eklinik.e_klinikappnew.R
import com.eklinik.e_klinikappnew.auth.AuthViewModel
import com.eklinik.e_klinikappnew.data.models.PatientProfile
import com.eklinik.e_klinikappnew.data.models.UserResponse

// Not: Renkleriniz tanımlı değilse diye buraya ekliyorum.
val AppGreen = Color(0xFF30ADA2)
val TextPrimary = Color.Black
val TextSecondary = Color.Gray

@Composable
fun ProfileScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val profileUiState by profileViewModel.uiState.collectAsState()
    val currentState = profileUiState

    when (currentState) {
        is ProfileUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is ProfileUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = currentState.message)
            }
        }
        is ProfileUiState.Success -> {
            val userResponse = currentState.userProfile
            val patientProfile = userResponse.patientProfile

            ProfileContent(
                navController = navController,
                authViewModel = authViewModel,
                userResponse = userResponse,
                patientProfile = patientProfile
            )
        }
    }
}

@Composable
fun ProfileContent(
    navController: NavController,
    authViewModel: AuthViewModel,
    userResponse: UserResponse,
    patientProfile: PatientProfile?
) {
    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFF8FAFC)) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            ProfileHeaderSection(userResponse, patientProfile)
            ProfileMenuSection(navController, authViewModel, userResponse, patientProfile)
        }
    }
}

@Composable
fun ProfileHeaderSection(
    userResponse: UserResponse,
    patientProfile: PatientProfile?
) {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF52D1C6), AppGreen)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp, bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Picture
            Box {
                Image(
                    painter = painterResource(id = R.drawable.profile_placeholder), // Kendi placeholder resminizi ekleyin
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                )
                Icon(
                    imageVector = Icons.Filled.PhotoCamera,
                    contentDescription = "Change Picture",
                    tint = Color.White,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .background(AppGreen, CircleShape)
                        .padding(6.dp)
                        .size(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // User Name
            Text(
                text = "${userResponse.firstName} ${userResponse.lastName}".trim(),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Stats Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatItem(icon = Icons.Default.Favorite, value = "215bpm", label = "Heart rate")
                VerticalDivider()
                StatItem(icon = Icons.Default.LocalFireDepartment, value = "756cal", label = "Calories")
                VerticalDivider()
                StatItem(
                    icon = Icons.Default.FitnessCenter,
                    value = "${patientProfile?.weight?.toInt() ?: "--"} lbs",
                    label = "Weight"
                )
            }
        }
    }
}

@Composable
fun ProfileMenuSection(
    navController: NavController,
    authViewModel: AuthViewModel,
    userResponse: UserResponse,
    patientProfile: PatientProfile?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = (-32).dp)
            .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
            .background(Color.White)
            .padding(top = 32.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        ProfileMenuItem(
            icon = Icons.Outlined.Person,
            title = "T.C. Kimlik No",
            value = userResponse.nationalId
        )
        ProfileMenuItem(
            icon = Icons.Outlined.Email,
            title = "E-posta",
            value = userResponse.email
        )
        ProfileMenuItem(
            icon = Icons.Outlined.Phone,
            title = "Telefon Numarası",
            value = userResponse.phoneNumber
        )
        ProfileMenuItem(
            icon = Icons.Outlined.Cake,
            title = "Doğum Tarihi",
            value = patientProfile?.dateOfBirth?.substring(0, 10) ?: "Belirtilmemiş"
        )
        ProfileMenuItem(
            icon = Icons.Outlined.LocationCity,
            title = "Doğum Yeri",
            value = "${patientProfile?.birthPlaceDistrict ?: ""} / ${patientProfile?.birthPlaceCity ?: "Belirtilmemiş"}"
        )
        ProfileMenuItem(
            icon = Icons.Outlined.Public,
            title = "Ülke",
            value = patientProfile?.country ?: "Belirtilmemiş"
        )
        ProfileMenuItem(
            icon = Icons.Outlined.Home,
            title = "Adres",
            value = patientProfile?.address ?: "Belirtilmemiş"
        )
        ProfileMenuItem(
            icon = Icons.Outlined.MedicalInformation,
            title = "Kronik Hastalık",
            value = if (patientProfile?.hasChronicIllness == true) "Var" else "Yok",
            iconColor = if (patientProfile?.hasChronicIllness == true) Color.Red.copy(alpha = 0.7f) else AppGreen
        )
        ProfileMenuItem(
            icon = Icons.Outlined.Vaccines,
            title = "İlaç Bağımlılığı",
            value = if (patientProfile?.isMedicationDependent == true) "Var" else "Yok",
            iconColor = if (patientProfile?.isMedicationDependent == true) Color.Red.copy(alpha = 0.7f) else AppGreen
        )
        ProfileMenuItem(
            icon = Icons.Outlined.Logout,
            title = "Logout",
            value = "Hesabınızdan güvenle çıkış yapın",
            iconColor = Color(0xFFEF4444),
            onClick = { authViewModel.logout() }
        )
    }
}

@Composable
fun StatItem(icon: ImageVector, value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = Color.White.copy(alpha = 0.9f),
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Text(
            text = label,
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 12.sp
        )
    }
}

@Composable
fun VerticalDivider() {
    Box(
        modifier = Modifier
            .height(40.dp)
            .width(1.dp)
            .background(Color.White.copy(alpha = 0.3f))
    )
}

@Composable
fun ProfileMenuItem(
    icon: ImageVector,
    title: String,
    value: String,
    iconColor: Color = AppGreen,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(iconColor.copy(alpha = 0.1f))
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = iconColor,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = TextPrimary
            )
            Text(
                text = value,
                fontSize = 14.sp,
                color = TextSecondary
            )
        }

        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = Color.Gray.copy(alpha = 0.7f),
            modifier = Modifier.size(24.dp)
        )
    }
}