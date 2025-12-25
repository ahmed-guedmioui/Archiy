package com.core.presentation.design_system.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun CoreDialog(
    modifier: Modifier = Modifier,
    title: String,
    titleColor: Color = MaterialTheme.colorScheme.onBackground,
    description: String? = null,
    descriptionTextAlign: TextAlign = TextAlign.Center,
    onDismiss: () -> Unit = {},
    image: Painter? = null,
    imageWidth: Dp = 150.dp,
    showCloseIcon: Boolean = false,
    properties: DialogProperties = DialogProperties(),
    betweenButtonsPadding: Dp = 0.dp,
    extraContent: (@Composable () -> Unit)? = null,
    primaryButton: @Composable RowScope.() -> Unit = {},
    secondaryButton: @Composable RowScope.() -> Unit = {},
) {
    Dialog(
        properties = properties,
        onDismissRequest = onDismiss
    ) {
        Box(
            modifier = modifier
        ) {
            Column(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.large)
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp)
                    .padding(vertical = 6.dp)
                    .then(
                        Modifier.padding(top =  if (showCloseIcon) 16.dp else 0.dp)
                    ),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (showCloseIcon) {
                    Spacer(Modifier.height(0.dp))
                }

                if (image != null) {
                    Image(
                        painter = image,
                        contentDescription = null,
                        modifier = Modifier.width(imageWidth)
                    )
                }

                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = titleColor
                )

                if (extraContent != null) {
                    extraContent()
                }

                if (description != null) {
                    Text(
                        text = description,
                        textAlign = descriptionTextAlign,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    secondaryButton()
                    if (betweenButtonsPadding != 0.dp) {
                        Spacer(Modifier.width(betweenButtonsPadding))
                    }
                    primaryButton()
                }
            }

            if (showCloseIcon) {
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.alpha(0.7f)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }
    }
}