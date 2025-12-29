package com.core.presentation.design_system.dialogs

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.core.presentation.R
import com.core.presentation.theme.theme.CoreColor
import com.core.presentation.theme.theme.Preview

@Composable
fun WarningDialog(
    modifier: Modifier = Modifier,
    icon: ImageVector? = Icons.Outlined.WarningAmber,
    title: String? = stringResource(R.string.warning),
    errorMessage: String?,
    onPrimary: () -> Unit = {},
    onDismiss: () -> Unit = {},
    primaryButtonText: String? = stringResource(R.string.ok),
    secondaryButtonText: String? = null,
    primaryColor: Color = CoreColor.warning
) {
    if (errorMessage != null) {
        CoreAlertDialog(
            modifier = modifier.padding(horizontal = 24.dp),
            icon = icon,
            title = title,
            description = errorMessage,
            onPrimary = onPrimary,
            onDismiss = onDismiss,
            primaryButtonText = primaryButtonText,
            secondaryButtonText = secondaryButtonText,
            primaryColor = primaryColor
        )
    }
}

@Preview
@Composable
private fun Preview() {
    WarningDialog(
        errorMessage = "Error message",
    )
}