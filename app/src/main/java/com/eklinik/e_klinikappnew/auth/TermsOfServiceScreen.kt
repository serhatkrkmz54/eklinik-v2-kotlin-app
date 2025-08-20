package com.eklinik.e_klinikappnew.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
fun TermsOfServiceScreen(
    navController: NavController
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Kullanım Şartları",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigateUp() }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Geri",
                            tint = TextPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "E-Klinik Kullanım Şartları ve Gizlilik Politikası",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = AppGreen,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "1. Genel Hükümler",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Bu sözleşme, E-Klinik uygulamasını kullanan kullanıcılar ile uygulama sağlayıcısı arasında yapılan anlaşmayı düzenler. Uygulamayı kullanarak bu şartları kabul etmiş sayılırsınız.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = TextSecondary,
                    lineHeight = 22.sp
                )
            )
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Text(
                text = "2. Kullanıcı Yükümlülükleri",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "• Doğru ve güncel bilgi sağlamak\n" +
                        "• Hesap güvenliğini korumak\n" +
                        "• Uygulamayı amacına uygun kullanmak\n" +
                        "• Başkalarının haklarına saygı göstermek\n" +
                        "• Yasalara uygun davranmak",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = TextSecondary,
                    lineHeight = 22.sp
                )
            )
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Text(
                text = "3. Gizlilik ve Kişisel Veriler",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Kişisel verileriniz KVKK (Kişisel Verilerin Korunması Kanunu) kapsamında korunur ve sadece sağlık hizmeti sunumu amacıyla kullanılır. Verileriniz üçüncü şahıslarla paylaşılmaz ve güvenli sunucularda saklanır.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = TextSecondary,
                    lineHeight = 22.sp
                )
            )
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Text(
                text = "4. Hizmet Kapsamı",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "E-Klinik uygulaması sağlık hizmetlerine erişimi kolaylaştırmak amacıyla geliştirilmiştir. Uygulama randevu alma, doktor görüşmeleri ve sağlık kayıtları yönetimi gibi hizmetler sunar.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = TextSecondary,
                    lineHeight = 22.sp
                )
            )
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Text(
                text = "5. Sorumluluk Sınırlamaları",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Uygulama sağlayıcısı, hizmet kalitesini sürdürmek için elinden geleni yapar ancak kesintisiz hizmet garantisi vermez. Teknik arızalar, bakım çalışmaları veya force majeure durumlarından kaynaklanan hizmet kesintilerinden sorumlu tutulamaz.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = TextSecondary,
                    lineHeight = 22.sp
                )
            )
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Text(
                text = "6. Fikri Mülkiyet Hakları",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Uygulamadaki tüm içerik, tasarım, logo ve yazılım E-Klinik'in fikri mülkiyetidir. İzinsiz kullanım, kopyalama veya dağıtım yasaktır.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = TextSecondary,
                    lineHeight = 22.sp
                )
            )
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Text(
                text = "7. Değişiklikler ve Güncellemeler",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Bu sözleşme gerektiğinde güncellenebilir. Değişiklikler uygulama üzerinden duyurulur ve yürürlük tarihinden itibaren geçerli olur. Güncellemelerden sonra uygulamayı kullanmaya devam etmeniz yeni şartları kabul ettiğiniz anlamına gelir.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = TextSecondary,
                    lineHeight = 22.sp
                )
            )
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Text(
                text = "8. İletişim",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Bu sözleşme ile ilgili sorularınız için:\n" +
                        "E-posta: info@eklinik.com\n" +
                        "Telefon: +90 (212) 555 0123\n" +
                        "Adres: İstanbul, Türkiye",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = TextSecondary,
                    lineHeight = 22.sp
                )
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = AppGreen.copy(alpha = 0.1f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Son Güncelleme",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = AppGreen
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Bu sözleşme en son 15 Ocak 2024 tarihinde güncellenmiştir.",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = TextSecondary
                        )
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TermsOfServiceScreenPreview() {
    EKlinikAppNewTheme {
        TermsOfServiceScreen(
            navController = rememberNavController()
        )
    }
}