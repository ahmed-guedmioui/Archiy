package com.profile.presentation

import com.core.presentation.util.error.UiText

sealed interface ProfileEvent {
    data object Logout : ProfileEvent
    data class OnError(val error: UiText) : ProfileEvent
    data class OnErrorText(val errorText: String) : ProfileEvent
}