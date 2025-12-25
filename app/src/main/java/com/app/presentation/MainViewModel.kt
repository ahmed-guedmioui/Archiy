package com.app.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.domain.service.session.SessionService
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val sessionService: SessionService
) : ViewModel() {

    private val eventChannel = Channel<MainEvent>()
    val event = eventChannel.receiveAsFlow()

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(MainState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                checkLogin()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            initialValue = MainState(),
            started = SharingStarted.WhileSubscribed(5_000L)
        )

    fun onAction(action: MainAction) {
        when (action) {
            else -> {}
        }
    }

    private fun checkLogin() {
        viewModelScope.launch {
            _state.update {
                it.copy(isCheckingLogin = true)
            }

            val isLoggedIn = sessionService.getToken() != null &&
                    sessionService.getUser() != null

            _state.update {
                it.copy(
                    isLoggedIn = isLoggedIn,
                    isCheckingLogin = false
                )
            }
        }
    }
}