package com.core.presentation.design_system

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CorePasswordTextField(
    modifier: Modifier = Modifier,
    textFieldState: TextFieldState,
    isPasswordVisible: Boolean,
    applyTextWeight: Boolean = true,
    height: Dp = 60.dp,
    borderWidth: Dp = 1.dp,
    shape: Shape = MaterialTheme.shapes.extraLarge,
    onTogglePasswordVisibility: () -> Unit,
    hint: String,
    style: TextStyle = LocalTextStyle.current,
    title: String? = null,
    focus: Boolean = false,
    onFocusChanged: (isFocused: Boolean) -> Unit = {},
    passwordVisibilityIconDescription: String? = null
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(focus) {
        if (focus) {
            focusRequester.requestFocus()
        } else {
            focusRequester.freeFocus()
        }
    }

    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(start = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            if (title != null) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        Spacer(Modifier.height(4.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .border(
                    width = borderWidth,
                    color = MaterialTheme.colorScheme.onBackground.copy(0.3f),
                    shape = shape
                )
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.Center
        ) {

            BasicSecureTextField(
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .onFocusEvent { focusState ->
                        onFocusChanged(focusState.isFocused)
                    },
                state = textFieldState,
                textObfuscationMode = if (isPasswordVisible) {
                    TextObfuscationMode.Visible
                } else {
                    TextObfuscationMode.Hidden
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                textStyle = style.copy(
                    color = MaterialTheme.colorScheme.onBackground
                ),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
                decorator = { innerBox ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Outlined.Lock,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground.copy(0.5f),
                            modifier = Modifier.size(22.dp)
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Box(
                            modifier = if (applyTextWeight) Modifier.weight(1f) else Modifier
                        ) {
                            if (textFieldState.text.isEmpty()) {
                                Text(
                                    text = hint,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.5f),
                                    style = style,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                            innerBox()
                        }


                        IconButton(
                            onClick = { onTogglePasswordVisibility() },
                        ) {
                            Icon(
                                imageVector = if (isPasswordVisible) {
                                    Icons.Outlined.Visibility
                                } else {
                                    Icons.Outlined.VisibilityOff
                                },
                                contentDescription = passwordVisibilityIconDescription,
                                tint = MaterialTheme.colorScheme.onBackground.copy(0.7f),
                            )
                        }
                    }
                }
            )
        }
    }
}