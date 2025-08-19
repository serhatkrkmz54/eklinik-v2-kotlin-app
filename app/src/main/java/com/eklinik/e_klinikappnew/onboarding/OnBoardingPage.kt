package com.eklinik.e_klinikappnew.onboarding

import android.media.Image
import androidx.annotation.DrawableRes
import com.eklinik.e_klinikappnew.R

data class OnBoardingPage(
    @DrawableRes val image: Int,
    val title: String,
)

val onBoardingPages = listOf(
    OnBoardingPage(
        image = R.drawable.onboarding1,
        title = "Güvenilir ve işinde uzman doktorlar ile tanışın",
    ),
    OnBoardingPage(
        image = R.drawable.onboarding2,
        title = "Birçok klinikte aradığınız doktoru bulun",
    ),
    OnBoardingPage(
        image = R.drawable.onboarding3,
        title = "Online randevu ile hızlıca çözüm bulun",
    )
)