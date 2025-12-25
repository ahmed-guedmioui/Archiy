package com.core.presentation.theme.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.core.presentation.R

val displayFontFamily = FontFamily(
    Font(
        resId = R.font.poppins_light,
        weight = FontWeight.Light
    ),
    Font(
        resId = R.font.poppins_regular,
        weight = FontWeight.Normal
    ),
    Font(
        resId = R.font.poppins_medium,
        weight = FontWeight.Medium
    ),
    Font(
        resId = R.font.poppins_semibold,
        weight = FontWeight.SemiBold
    ),
    Font(
        resId = R.font.poppins_bold,
        weight = FontWeight.Bold
    )
)

private val baseline = Typography()
private val AppTypography = Typography(
    displayLarge = baseline.displayLarge.copy(fontFamily = displayFontFamily),
    displayMedium = baseline.displayMedium.copy(fontFamily = displayFontFamily),
    displaySmall = baseline.displaySmall.copy(fontFamily = displayFontFamily),
    headlineLarge = baseline.headlineLarge.copy(fontFamily = displayFontFamily),
    headlineMedium = baseline.headlineMedium.copy(fontFamily = displayFontFamily),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = displayFontFamily),
    titleLarge = baseline.titleLarge.copy(fontFamily = displayFontFamily),
    titleMedium = baseline.titleMedium.copy(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    titleSmall = baseline.titleSmall.copy(fontFamily = displayFontFamily),
    bodyLarge = baseline.bodyLarge.copy(fontFamily = displayFontFamily),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = displayFontFamily),
    bodySmall = baseline.bodySmall.copy(fontFamily = displayFontFamily),
    labelLarge = baseline.labelLarge.copy(fontFamily = displayFontFamily),
    labelMedium = baseline.labelMedium.copy(fontFamily = displayFontFamily),
    labelSmall = baseline.labelSmall.copy(fontFamily = displayFontFamily),
)

@Composable
fun Typography(): Typography {
    val config = LocalConfiguration.current
    val isTablet = config.smallestScreenWidthDp >= 600

    val scale = if (isTablet) 1.3f else 1.0f

    return Typography(
        displayLarge = AppTypography.displayLarge.copy(fontSize = AppTypography.displayLarge.fontSize * scale),
        displayMedium = AppTypography.displayMedium.copy(fontSize = AppTypography.displayMedium.fontSize * scale),
        displaySmall = AppTypography.displaySmall.copy(fontSize = AppTypography.displaySmall.fontSize * scale),
        headlineLarge = AppTypography.headlineLarge.copy(fontSize = AppTypography.headlineLarge.fontSize * scale),
        headlineMedium = AppTypography.headlineMedium.copy(fontSize = AppTypography.headlineMedium.fontSize * scale),
        headlineSmall = AppTypography.headlineSmall.copy(fontSize = AppTypography.headlineSmall.fontSize * scale),
        titleLarge = AppTypography.titleLarge.copy(fontSize = AppTypography.titleLarge.fontSize * scale),
        titleMedium = AppTypography.titleMedium.copy(fontSize = AppTypography.titleMedium.fontSize * scale),
        titleSmall = AppTypography.titleSmall.copy(fontSize = AppTypography.titleSmall.fontSize * scale),
        bodyLarge = AppTypography.bodyLarge.copy(fontSize = AppTypography.bodyLarge.fontSize * scale),
        bodyMedium = AppTypography.bodyMedium.copy(fontSize = AppTypography.bodyMedium.fontSize * scale),
        bodySmall = AppTypography.bodySmall.copy(fontSize = AppTypography.bodySmall.fontSize * scale),
        labelLarge = AppTypography.labelLarge.copy(fontSize = AppTypography.labelLarge.fontSize * scale),
        labelMedium = AppTypography.labelMedium.copy(fontSize = AppTypography.labelMedium.fontSize * scale),
        labelSmall = AppTypography.labelSmall.copy(fontSize = AppTypography.labelSmall.fontSize * scale),
    )
}
