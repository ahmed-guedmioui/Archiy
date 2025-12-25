package com.core.presentation.design_system

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CoreButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    isLoading: Boolean = false,
    isClickableWhenLoading: Boolean = false,
    enabled: Boolean = true,
    style: TextStyle = MaterialTheme.typography.bodyLarge.copy(
        fontWeight = FontWeight.SemiBold
    ),
    verticalPadding: Dp = 8.dp,
    textMaxLines: Int = 1,
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    buttonColor: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit,
    content: @Composable () -> Unit = {}
) {
    Button(
        onClick = {
            if (isLoading && isClickableWhenLoading) {
                onClick()
            } else {
                onClick()
            }
        },
        enabled = enabled && !isLoading,
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
        shape = MaterialTheme.shapes.extraLarge,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .padding(verticalPadding),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(28.dp),
                    strokeWidth = 3.dp
                )
            }

            if (text != null) {
                Text(
                    text = text,
                    style = style,
                    maxLines = textMaxLines,
                    color = textColor.copy(if (isLoading) 0f else 1f)
                )
            } else {
                content()
            }
        }
    }
}


@Composable
fun CoreOutlinedButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    isLoading: Boolean = false,
    enabled: Boolean = true,
    verticalPadding: Dp = 8.dp,
    borderWidth: Dp = 1.dp,
    style: TextStyle = MaterialTheme.typography.bodyLarge.copy(
        fontWeight = FontWeight.SemiBold
    ),
    textMaxLines: Int = 1,
    textColor: Color = MaterialTheme.colorScheme.primary,
    borderColor: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit,
    content: @Composable () -> Unit = {}
) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled && !isLoading,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground,
        ),
        border = BorderStroke(
            width = borderWidth,
            color = if (enabled) borderColor else borderColor.copy(0.3f)
        ),
        shape = MaterialTheme.shapes.extraLarge,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.padding(verticalPadding),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(28.dp),
                    strokeWidth = 3.dp
                )
            }
            if (text != null) {
                Text(
                    text = text,
                    style = style,
                    color = if (enabled) textColor else textColor.copy(0.3f),
                    maxLines = textMaxLines,
                    modifier = Modifier.alpha(if (isLoading) 0f else 1f),
                )
            } else {
                Box(modifier = Modifier.alpha(if (isLoading) 0f else 1f)) {
                    content()
                }
            }
        }
    }
}