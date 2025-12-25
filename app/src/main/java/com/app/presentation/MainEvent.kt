package com.app.presentation

import com.core.domain.util.result.Error

sealed interface MainEvent {
    data class OnError(val error: Error) : MainEvent
}