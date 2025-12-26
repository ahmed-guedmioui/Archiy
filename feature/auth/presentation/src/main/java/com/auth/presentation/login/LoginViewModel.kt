package com.auth.presentation.login

import androidx.compose.foundation.text.input.clearText
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth.domain.usecase.CheckCanLoginUseCase
import com.auth.domain.usecase.LoginUseCase
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

class LoginViewModel(
    private val checkCanLoginUseCase: CheckCanLoginUseCase,
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    private val eventChannel = Channel<LoginEvent>()
    val event = eventChannel.receiveAsFlow()

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(LoginState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            initialValue = LoginState(),
            started = SharingStarted.WhileSubscribed(5_000L)
        )

    init {
        viewModelScope.launch {
            snapshotFlow { _state.value.email.text }.collectLatest { email ->
                checkCanLogin(email.toString().trim(), _state.value.password.text.toString())
            }
        }

        viewModelScope.launch {
            snapshotFlow { _state.value.password.text }.collectLatest { password ->
                checkCanLogin(_state.value.email.text.toString().trim(), password.toString())
            }
        }
    }

    fun onAction(action: LoginAction) {
        when (action) {
            LoginAction.OnChangePasswordVisibility -> changePasswordVisibility()
            LoginAction.OnLogin -> login()
            LoginAction.OnRegister -> {} // Navigates to register screen
        }
    }

    private fun login() {
        viewModelScope.launch {
            _state.update { it.copy(isLoggingIn = true) }

            loginUseCase(
                email = _state.value.email.text.toString(),
                password = _state.value.password.text.toString()
            ).onSuccess {
                _state.value.email.clearText()
                _state.value.password.clearText()

                eventChannel.send(LoginEvent.OnLoggedIn)
            }.onError { error ->
                val message = when (error) {
                    is LoginUseCase.LoginError -> {
                        when (error) {
                            LoginUseCase.LoginError.EmptyPassword -> UiText.Resource(R.string.password_is_empty)
                            LoginUseCase.LoginError.InvalidEmail -> UiText.Resource(R.string.invalid_email)
                            LoginUseCase.LoginError.MissingUserData -> UiText.Resource(R.string.user_data_is_missing)
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

                eventChannel.send(LoginEvent.OnError(message))
            }

            _state.update { it.copy(isLoggingIn = false) }
        }
    }

    private fun changePasswordVisibility() {
        _state.update {
            it.copy(
                isPasswordVisible = !it.isPasswordVisible
            )
        }
    }

    private fun checkCanLogin(
        email: String, password: String
    ) {
        _state.update {
            it.copy(
                canLogin = checkCanLoginUseCase(
                    email = email,
                    password = password,
                    isLoggingIn = _state.value.isLoggingIn
                )
            )
        }
    }
}