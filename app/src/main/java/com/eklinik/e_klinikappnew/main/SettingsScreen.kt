package com.eklinik.e_klinikappnew.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.eklinik.e_klinikappnew.ui.theme.AppGreen
import com.eklinik.e_klinikappnew.ui.theme.EKlinikAppNewTheme
import com.eklinik.e_klinikappnew.ui.theme.TextPrimary
import com.eklinik.e_klinikappnew.ui.theme.TextSecondary

@Composable
fun SettingsScreen(navController: NavController) {
    var notificationsEnabled by remember { mutableStateOf(true) }
    var darkModeEnabled by remember { mutableStateOf(false) }
    var biometricEnabled by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = "Ayarlar",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            ),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // General Settings Section
        SettingsSection(
            title = "Genel",
            items = listOf(
                SettingsSwitchItem(
                    title = "Bildirimler",
                    description = "Push bildirimleri al",
                    icon = Icons.Default.Notifications,
                    isChecked = notificationsEnabled,
                    onCheckedChange = { notificationsEnabled = it }
                ),
                SettingsSwitchItem(
                    title = "Karanlık Mod",
                    description = "Koyu tema kullan",
                    icon = Icons.Default.DarkMode,
                    isChecked = darkModeEnabled,
                    onCheckedChange = { darkModeEnabled = it }
                )
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Security Settings Section
        SettingsSection(
            title = "Güvenlik",
            items = listOf(
                SettingsSwitchItem(
                    title = "Biyometrik Giriş",
                    description = "Parmak izi ile giriş yap",
                    icon = Icons.Default.Fingerprint,
                    isChecked = biometricEnabled,
                    onCheckedChange = { biometricEnabled = it }
                )
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Other Settings Section
        SettingsSection(
            title = "Diğer",
            clickableItems = listOf(
                SettingsClickableItem(
                    title = "Şifre Değiştir",
                    description = "Hesap şifreni güncelle",
                    icon = Icons.Default.Lock,
                    onClick = { /* Handle password change */ }
                ),
                SettingsClickableItem(
                    title = "Dil",
                    description = "Türkçe",
                    icon = Icons.Default.Language,
                    onClick = { /* Handle language change */ }
                ),
                SettingsClickableItem(
                    title = "Gizlilik Politikası",
                    description = "Gizlilik koşullarını görüntüle",
                    icon = Icons.Default.PrivacyTip,
                    onClick = { /* Handle privacy policy */ }
                ),
                SettingsClickableItem(
                    title = "Kullanım Koşulları",
                    description = "Hizmet şartlarını görüntüle",
                    icon = Icons.Default.Description,
                    onClick = { /* Handle terms of service */ }
                ),
                SettingsClickableItem(
                    title = "Uygulama Hakkında",
                    description = "Versiyon 1.0.0",
                    icon = Icons.Default.Info,
                    onClick = { /* Handle about */ }
                )
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        // App Version
        Text(
            text = "E-Klinik v1.0.0",
            style = MaterialTheme.typography.bodySmall.copy(
                color = TextSecondary
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun SettingsSection(
    title: String,
    items: List<SettingsSwitchItem> = emptyList(),
    clickableItems: List<SettingsClickableItem> = emptyList()
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            ),
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column {
                items.forEachIndexed { index, item ->
                    SettingsSwitchRow(item = item)
                    if (index < items.size - 1 || clickableItems.isNotEmpty()) {
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = Color.Gray.copy(alpha = 0.2f)
                        )
                    }
                }

                clickableItems.forEachIndexed { index, item ->
                    SettingsClickableRow(item = item)
                    if (index < clickableItems.size - 1) {
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = Color.Gray.copy(alpha = 0.2f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SettingsSwitchRow(item: SettingsSwitchItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon
        Icon(
            imageVector = item.icon,
            contentDescription = null,
            tint = AppGreen,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Content
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Medium,
                    color = TextPrimary
                )
            )
            Text(
                text = item.description,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = TextSecondary
                )
            )
        }

        // Switch
        Switch(
            checked = item.isChecked,
            onCheckedChange = item.onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = AppGreen,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color.Gray.copy(alpha = 0.3f)
            )
        )
    }
}

@Composable
fun SettingsClickableRow(item: SettingsClickableItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon
        Icon(
            imageVector = item.icon,
            contentDescription = null,
            tint = AppGreen,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Content
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Medium,
                    color = TextPrimary
                )
            )
            Text(
                text = item.description,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = TextSecondary
                )
            )
        }

        // Arrow
        IconButton(
            onClick = item.onClick
        ) {
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = TextSecondary,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

data class SettingsSwitchItem(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val isChecked: Boolean,
    val onCheckedChange: (Boolean) -> Unit
)

data class SettingsClickableItem(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    EKlinikAppNewTheme {
        SettingsScreen(navController = rememberNavController())
    }
}