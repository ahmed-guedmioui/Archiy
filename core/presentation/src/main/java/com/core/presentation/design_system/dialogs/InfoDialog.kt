package com.core.presentation.design_system.dialogs

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.core.presentation.R
import com.core.presentation.theme.theme.Preview

@Composable
fun InfoDialog(
    modifier: Modifier = Modifier,
    icon: ImageVector? = Icons.Outlined.Info,
    title: String? = stringResource(R.string.info),
    errorMessage: String?,
    onPrimary: () -> Unit = {},
    onDismiss: () -> Unit = {},
    primaryButtonText: String? = stringResource(R.string.ok),
    secondaryButtonText: String? = null,
    primaryColor: Color = MaterialTheme.colorScheme.primary
) {
    if (errorMessage != null) {
        CoreAlertDialog(
            modifier = modifier.padding(horizontal = 16.dp),
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
    InfoDialog(
        errorMessage = "Info message",
    )
}