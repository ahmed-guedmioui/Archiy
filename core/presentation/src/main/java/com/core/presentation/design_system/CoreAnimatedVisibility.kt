package com.core.presentation.design_system

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable

@Composable
fun CoreAnimatedVisibility(
    isVisible: Boolean,
    enter: EnterTransition = slideInVertically(),
    exit: ExitTransition = slideOutVertically(),
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = enter,
        exit = exit
    ) {
        content()
    }
}