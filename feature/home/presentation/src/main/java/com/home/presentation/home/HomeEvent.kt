package com.home.presentation.home

import com.core.presentation.util.error.UiText

sealed interface HomeEvent {
    data class OnError(val error: UiText) : HomeEvent
    data class OnErrorText(val errorText: String) : HomeEvent
}