package com.auth.presentation.register

import androidx.compose.foundation.text.input.TextFieldState

data class RegisterState(
    val username: TextFieldState = TextFieldState(""),
    val email: TextFieldState = TextFieldState(""),
    val password: TextFieldState = TextFieldState(""),
    val isPasswordVisible: Boolean = false,
    val canRegister: Boolean = false,
    val isRegistering: Boolean = false
)