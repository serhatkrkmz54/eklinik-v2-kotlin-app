package com.eklinik.e_klinikappnew.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.eklinik.e_klinikappnew.auth.AuthViewModel
import com.eklinik.e_klinikappnew.data.models.UserResponse
import com.eklinik.e_klinikappnew.main.ProfileViewModel
import com.eklinik.e_klinikappnew.main.ProfileUiState
import com.eklinik.e_klinikappnew.ui.theme.AppGreen
import com.eklinik.e_klinikappnew.ui.theme.EKlinikAppNewTheme
import com.eklinik.e_klinikappnew.ui.theme.TextPrimary
import com.eklinik.e_klinikappnew.ui.theme.TextSecondary

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
            return
        }
        is ProfileUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = currentState.message)
            }
            return
        }
        is ProfileUiState.Success -> {
            val userResponse = currentState.userProfile
            val patientProfile = userResponse.patientProfile
            val doctorInfo = userResponse.doctorInfo
            
            ProfileContent(
                navController = navController,
                authViewModel = authViewModel,
                userResponse = userResponse,
                patientProfile = patientProfile,
                doctorInfo = doctorInfo
            )
        }
    }
}

@Composable
fun ProfileContent(
    navController: NavController,
    authViewModel: AuthViewModel,
    userResponse: UserResponse,
    patientProfile: com.eklinik.e_klinikappnew.data.models.PatientProfile?,
    doctorInfo: com.eklinik.e_klinikappnew.data.models.DoctorInfo?
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        // Main content that can scroll behind bottom bar
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 400.dp)
        ) {
            // Top Profile Section with gradient background
            Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF52D1C6),
                            Color(0xFF30ADA2)
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 48.dp, start = 24.dp, end = 24.dp, bottom = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Profile Avatar
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(
                            color = Color.White.copy(alpha = 0.2f),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (doctorInfo != null) Icons.Default.MedicalServices else Icons.Default.Person,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(50.dp)
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // User Name
                Text(
                    text = "${userResponse?.firstName ?: ""} ${userResponse?.lastName ?: ""}".trim().ifEmpty { "Kullanıcı" },
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Stats Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StatItem(
                        icon = Icons.Default.FitnessCenter,
                        value = "${patientProfile?.weight?.toInt() ?: "--"}kg",
                        label = "Kilo"
                    )
                    StatItem(
                        icon = Icons.Default.Height,
                        value = "${patientProfile?.height?.toInt() ?: "--"}cm",
                        label = "Boy"
                    )
                    StatItem(
                        icon = Icons.Default.CalendarToday,
                        value = patientProfile?.dateOfBirth?.substring(0, 10) ?: "--",
                        label = "Doğum Tarihi"
                    )
                }
            }
            
            // Atom icon in background
            Icon(
                imageVector = Icons.Default.Science,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.1f),
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.TopEnd)
                    .offset(x = 50.dp, y = (-20).dp)
            )
        }
        
        // Bottom Menu Section - Fixed at bottom
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                MenuItemRow(
                    icon = Icons.Default.Favorite,
                    title = "My Saved",
                    iconColor = Color(0xFF4DD0E1)
                )
                
                MenuItemRow(
                    icon = Icons.Default.CalendarToday,
                    title = "Appointment",
                    iconColor = Color(0xFF4DD0E1)
                )
                
                MenuItemRow(
                    icon = Icons.Default.CreditCard,
                    title = "Payment Method",
                    iconColor = Color(0xFF4DD0E1)
                )
                
                MenuItemRow(
                    icon = Icons.Default.Help,
                    title = "FAQs",
                    iconColor = Color(0xFF4DD0E1)
                )
                
                MenuItemRow(
                    icon = Icons.Default.ExitToApp,
                    title = "Logout",
                    iconColor = Color(0xFFE57373),
                    onClick = { authViewModel.logout() }
                )
            }
        }
    }
}
}




@Composable
fun StatItem(
    icon: ImageVector,
    value: String,
    label: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(
                color = Color.White.copy(alpha = 0.8f)
            )
        )
    }
}

@Composable
fun MenuItemRow(
    icon: ImageVector,
    title: String,
    iconColor: Color,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .then(
                if (onClick != null) {
                    Modifier.clickable { onClick() }
                } else {
                    Modifier
                }
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = TextPrimary
            )
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = TextSecondary,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
fun SimpleInfoRow(
    label: String,
    value: String,
    isWarning: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = TextSecondary
            )
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium,
                color = if (isWarning) Color(0xFFD32F2F) else TextPrimary
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    EKlinikAppNewTheme {
        // Preview için mock data kullanılabilir
    }
}