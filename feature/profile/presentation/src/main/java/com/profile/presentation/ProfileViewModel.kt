package com.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.domain.util.result.onError
import com.core.domain.util.result.onSuccess
import com.core.presentation.util.error.UiText
import com.core.presentation.util.error.toUiText
import com.profile.domain.usecase.GetUserUseCase
import com.profile.domain.usecase.LogoutUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val eventChannel = Channel<ProfileEvent>()
    val event = eventChannel.receiveAsFlow()

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(ProfileState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                loadProfile()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            initialValue = ProfileState(),
            started = SharingStarted.WhileSubscribed(5_000L)
        )

    fun onAction(action: ProfileAction) {
        when (action) {
            ProfileAction.OnLogout -> logout()
        }
    }

    private fun logout() {
        viewModelScope.launch {
            logoutUseCase()
            eventChannel.send(ProfileEvent.Logout)
        }
    }

    private fun loadProfile() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val user = getUserUseCase()
            if (user != null) {
                _state.update {
                    it.copy(
                        user = user,
                        isLoading = false
                    )
                }
            } else {
                _state.update { it.copy(isLoading = false) }
                eventChannel.send(
                    ProfileEvent.OnError(
                        UiText.Resource(R.string.could_not_load_profile)
                    )
                )
            }
        }
    }
}