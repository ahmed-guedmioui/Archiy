package com.home.presentation.detail

import com.core.presentation.util.error.UiText

sealed interface ItemDetailEvent {
    data class OnError(val error: UiText) : ItemDetailEvent
    data class OnErrorText(val errorText: String) : ItemDetailEvent
}
