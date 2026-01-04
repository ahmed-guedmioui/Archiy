package com.core.presentation.extensions

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import kotlinx.coroutines.delay

fun Modifier.animatedAppearance(
    initialDelay: Int = 0,
    initialAlpha: Float = 0f,
    targetAlpha: Float = 1f,
    initialScale: Float = 0.9f,
    targetScale: Float = 1f,
    alphaDuration: Int = 300,
    scaleDuration: Int = 300
): Modifier = composed {
    var isVisible by retain { mutableStateOf(false) }

    LaunchedEffect(true) {
        delay(initialDelay.toLong())
        isVisible = true
    }

    val alpha by animateFloatAsState(
        targetValue = if (!isVisible) initialAlpha else targetAlpha,
        animationSpec = tween(
            durationMillis = alphaDuration,
            easing = FastOutSlowInEasing
        ),
        label = "alpha"
    )

    val scale by animateFloatAsState(
        targetValue = if (!isVisible) initialScale else targetScale,
        animationSpec = tween(
            durationMillis = scaleDuration,
            easing = FastOutSlowInEasing
        ),
        label = "scale"
    )

    this
        .alpha(alpha)
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
}