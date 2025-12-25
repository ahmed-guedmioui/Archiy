package com.auth.presentation.register

import com.core.presentation.util.error.UiText


sealed interface RegisterEvent {
    data object OnRegistered : RegisterEvent
    data class OnError(val error: UiText) : RegisterEvent
}