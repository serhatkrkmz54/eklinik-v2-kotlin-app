package com.eklinik.e_klinikappnew.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
fun AppointmentsScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = "Randevularım",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            ),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Upcoming Appointments
        Text(
            text = "Yaklaşan Randevular",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Sample appointments list
        val sampleAppointments = listOf(
            AppointmentItem("Dr. Ahmet Yılmaz", "Kardiyoloji", "15 Ocak 2024", "14:30"),
            AppointmentItem("Dr. Ayşe Demir", "Dahiliye", "18 Ocak 2024", "10:00"),
            AppointmentItem("Dr. Mehmet Kaya", "Ortopedi", "22 Ocak 2024", "16:15")
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(sampleAppointments) { appointment ->
                AppointmentCard(appointment = appointment)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // New Appointment Button
        Button(
            onClick = { /* Navigate to new appointment */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AppGreen
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.CalendarToday,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Yeni Randevu Al",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
    }
}

@Composable
fun AppointmentCard(appointment: AppointmentItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Doctor Icon
            Card(
                modifier = Modifier.size(48.dp),
                shape = RoundedCornerShape(8.dp),
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
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Appointment Details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = appointment.doctorName,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimary
                    )
                )
                Text(
                    text = appointment.department,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = TextSecondary
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Text(
                        text = appointment.date,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = AppGreen,
                            fontWeight = FontWeight.Medium
                        )
                    )
                    Text(
                        text = " • ",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = TextSecondary
                        )
                    )
                    Text(
                        text = appointment.time,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = AppGreen,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }
        }
    }
}

data class AppointmentItem(
    val doctorName: String,
    val department: String,
    val date: String,
    val time: String
)

@Preview(showBackground = true)
@Composable
fun AppointmentsScreenPreview() {
    EKlinikAppNewTheme {
        AppointmentsScreen(navController = rememberNavController())
    }
}