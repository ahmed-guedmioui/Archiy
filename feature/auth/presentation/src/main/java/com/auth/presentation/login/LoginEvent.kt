package com.auth.presentation.login

import com.core.presentation.util.error.UiText


sealed interface LoginEvent {
    data object OnLoggedIn : LoginEvent
    data class OnError(val error: UiText) : LoginEvent
}