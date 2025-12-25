package com.core.presentation.design_system.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.core.presentation.R
import com.core.presentation.design_system.CoreButton
import com.core.presentation.design_system.CoreOutlinedButton
import com.core.presentation.theme.theme.Preview

@Composable
fun CoreAlertDialog(
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    title: String? = null,
    titleColor: Color = MaterialTheme.colorScheme.onBackground,
    description: String?,
    descriptionTextAlign: TextAlign = TextAlign.Start,
    shape: Shape = MaterialTheme.shapes.large,
    onPrimary: () -> Unit = {},
    onDismiss: () -> Unit = {},
    betweenButtonsPadding: Dp = 16.dp,
    primaryButtonText: String? = null,
    secondaryButtonText: String? = null,
    primaryColor: Color = MaterialTheme.colorScheme.primary,
    onPrimaryColor: Color = MaterialTheme.colorScheme.onPrimary
) {
    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = modifier
                .clip(shape)
                .background(MaterialTheme.colorScheme.surface)
                .border(
                    width = 1.dp,
                    color = primaryColor,
                    shape = shape
                )
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (icon != null) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = primaryColor,
                        modifier = Modifier.size(32.dp)
                    )
                }

                if (title != null) {
                    Text(
                        text = title,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = titleColor
                    )
                }
            }

            if (description != null) {
                Text(
                    text = description,
                    textAlign = descriptionTextAlign,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            if (secondaryButtonText != null || primaryButtonText != null) {

                Spacer(Modifier)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CoreOutlinedButton(
                        text = secondaryButtonText,
                        onClick = { onDismiss() },
                        enabled = secondaryButtonText != null,
                        verticalPadding = 8.dp,
                        borderColor = primaryColor,
                        modifier = Modifier
                            .weight(1f)
                            .alpha(if (secondaryButtonText != null) 1f else 0f)
                    )

                    if (betweenButtonsPadding != 0.dp) {
                        Spacer(Modifier.width(betweenButtonsPadding))
                    }

                    CoreButton(
                        text = primaryButtonText,
                        onClick = { onPrimary() },
                        enabled = primaryButtonText != null,
                        verticalPadding = 8.dp,
                        buttonColor = primaryColor,
                        textColor = onPrimaryColor,
                        modifier = Modifier
                            .weight(1f)
                            .alpha(if (primaryButtonText != null) 1f else 0f)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    CoreAlertDialog(
        modifier = Modifier,
        icon = Icons.Outlined.ErrorOutline,
        title = stringResource(R.string.error),
        description = "Error message goes right here",
        onPrimary = {},
        onDismiss = {},
        primaryButtonText = stringResource(R.string.ok),
        secondaryButtonText = null,
        primaryColor = MaterialTheme.colorScheme.error,
        onPrimaryColor = MaterialTheme.colorScheme.onError
    )
}