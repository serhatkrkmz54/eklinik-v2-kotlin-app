package com.eklinik.e_klinikappnew.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.eklinik.e_klinikappnew.ui.theme.AppGreen
import com.eklinik.e_klinikappnew.ui.theme.EKlinikAppNewTheme
import com.eklinik.e_klinikappnew.ui.theme.TextPrimary
import com.eklinik.e_klinikappnew.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorsScreen(navController: NavController) {
    var searchText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = "Doktorlar",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            ),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Search Bar
        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            placeholder = {
                Text(
                    text = "Doktor veya bölüm ara...",
                    color = TextSecondary
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = TextSecondary
                )
            },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AppGreen,
                unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f)
            )
        )

        // Departments Section
        Text(
            text = "Popüler Bölümler",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Sample doctors list
        val sampleDoctors = listOf(
            DoctorItem("Dr. Ahmet Yılmaz", "Kardiyoloji", "15 yıl deneyim", 4.8f, "Kalp hastalıkları uzmanı"),
            DoctorItem("Dr. Ayşe Demir", "Dahiliye", "12 yıl deneyim", 4.9f, "Genel dahiliye uzmanı"),
            DoctorItem("Dr. Mehmet Kaya", "Ortopedi", "18 yıl deneyim", 4.7f, "Kemik ve eklem uzmanı"),
            DoctorItem("Dr. Fatma Özkan", "Kadın Doğum", "10 yıl deneyim", 4.9f, "Kadın sağlığı uzmanı"),
            DoctorItem("Dr. Ali Şahin", "Göz Hastalıkları", "14 yıl deneyim", 4.6f, "Göz sağlığı uzmanı")
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(sampleDoctors) { doctor ->
                DoctorCard(
                    doctor = doctor,
                    onAppointmentClick = { /* Navigate to appointment booking */ }
                )
            }
        }
    }
}

@Composable
fun DoctorCard(
    doctor: DoctorItem,
    onAppointmentClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Doctor Avatar
                Card(
                    modifier = Modifier.size(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = AppGreen.copy(alpha = 0.1f)
                    )
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = AppGreen,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Doctor Info
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = doctor.name,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary
                        )
                    )
                    Text(
                        text = doctor.department,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = AppGreen,
                            fontWeight = FontWeight.Medium
                        )
                    )
                    Text(
                        text = doctor.experience,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = TextSecondary
                        )
                    )
                }

                // Rating
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFB800),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = doctor.rating.toString(),
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Medium,
                            color = TextPrimary
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Description
            Text(
                text = doctor.description,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = TextSecondary
                ),
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Appointment Button
            Button(
                onClick = onAppointmentClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppGreen
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Randevu Al",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
    }
}

data class DoctorItem(
    val name: String,
    val department: String,
    val experience: String,
    val rating: Float,
    val description: String
)

@Preview(showBackground = true)
@Composable
fun DoctorsScreenPreview() {
    EKlinikAppNewTheme {
        DoctorsScreen(navController = rememberNavController())
    }
}