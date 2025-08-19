package com.eklinik.e_klinikappnew

import androidx.annotation.Size
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun DotSpinner(
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
    dotCount: Int = 8,
    dotSize: Dp = 5.dp,
    dotColor: Color = MaterialTheme.colorScheme.onPrimary
) {
    val infiniteTransition = rememberInfiniteTransition(label = "dot_spinner_transition")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "dot_spinner_angle"
    )

    Canvas(modifier = modifier.size(size)) {
        val radius = this.size.minDimension / 2.0f - dotSize.toPx()
        val centerX = this.size.width / 2
        val centerY = this.size.height / 2

        (0 until dotCount).forEach { i ->
            val currentAngle = (angle + (360f / dotCount) * i) % 360f
            val alpha = (i+1) / dotCount.toFloat()

            val x = centerX + radius * cos(Math.toRadians(currentAngle.toDouble())).toFloat()
            val y = centerY + radius * sin(Math.toRadians(currentAngle.toDouble())).toFloat()

            drawCircle(
                color = dotColor,
                radius = dotSize.toPx() / 2,
                center = Offset(x, y),
                alpha = alpha
            )
        }
    }
}