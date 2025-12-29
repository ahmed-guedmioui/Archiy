package com.auth.presentation.register

import androidx.compose.foundation.text.input.clearText
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth.domain.usecase.CheckCanRegisterUseCase
import com.auth.domain.usecase.RegisterUseCase
import com.auth.presentation.R
import com.core.domain.util.result.DataError
import com.core.domain.util.result.onError
import com.core.domain.util.result.onSuccess
import com.core.presentation.util.error.UiText
import com.core.presentation.util.error.toUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val checkCanRegisterUseCase: CheckCanRegisterUseCase,
    private val registerUseCase: RegisterUseCase,
) : ViewModel() {

    private val eventChannel = Channel<RegisterEvent>()
    val event = eventChannel.receiveAsFlow()

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(RegisterState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            initialValue = RegisterState(),
            started = SharingStarted.WhileSubscribed(5_000L)
        )

    init {
        viewModelScope.launch {
            snapshotFlow { _state.value.username.text }.collectLatest { username ->
                checkCanRegister(
                    username.toString().trim(),
                    _state.value.email.text.toString(),
                    _state.value.password.text.toString()
                )
            }
        }

        viewModelScope.launch {
            snapshotFlow { _state.value.email.text }.collectLatest { email ->
                checkCanRegister(
                    _state.value.username.text.toString(),
                    email.toString().trim(),
                    _state.value.password.text.toString()
                )
            }
        }

        viewModelScope.launch {
            snapshotFlow { _state.value.password.text }.collectLatest { password ->
                checkCanRegister(
                    _state.value.username.text.toString(),
                    _state.value.email.text.toString().trim(),
                    password.toString()
                )
            }
        }
    }

    fun onAction(action: RegisterAction) {
        when (action) {
            RegisterAction.OnChangePasswordVisibility -> changePasswordVisibility()
            RegisterAction.OnRegister -> register()
            RegisterAction.OnBack -> {} // Navigates to previous screen
        }
    }

    private fun register() {
        viewModelScope.launch {
            _state.update { it.copy(isRegistering = true) }

            registerUseCase(
                username = _state.value.username.text.toString(),
                email = _state.value.email.text.toString(),
                password = _state.value.password.text.toString()
            ).onSuccess {
                _state.value.username.clearText()
                _state.value.email.clearText()
                _state.value.password.clearText()

                eventChannel.trySend(RegisterEvent.OnRegistered)
            }.onError { error ->
                val message = when (error) {
                    is RegisterUseCase.RegisterError -> {
                        when (error) {
                            RegisterUseCase.RegisterError.EmptyUsername -> UiText.Resource(R.string.username_is_empty)
                            RegisterUseCase.RegisterError.EmptyPassword -> UiText.Resource(R.string.password_is_empty)
                            RegisterUseCase.RegisterError.InvalidEmail -> UiText.Resource(R.string.invalid_email)
                            RegisterUseCase.RegisterError.MissingUserData -> UiText.Resource(R.string.user_data_is_missing)
                        }
                    }

                    is DataError -> {
                        when (error) {
                            DataError.Remote.UNAUTHORIZED -> UiText.Resource(R.string.invalid_credentials)
                            else -> error.toUiText()
                        }
                    }

                    else -> {
                        UiText.Resource(com.core.presentation.R.string.error_unknown)
                    }
                }

                eventChannel.trySend(RegisterEvent.OnError(message))
            }

            _state.update { it.copy(isRegistering = false) }
        }
    }

    private fun changePasswordVisibility() {
        _state.update {
            it.copy(
                isPasswordVisible = !it.isPasswordVisible
            )
        }
    }

    private fun checkCanRegister(
        username: String,
        email: String,
        password: String
    ) {
        _state.update {
            it.copy(
                canRegister = checkCanRegisterUseCase(
                    username = username,
                    email = email,
                    password = password,
                    isLoggingIn = _state.value.isRegistering
                )
            )
        }
    }
}