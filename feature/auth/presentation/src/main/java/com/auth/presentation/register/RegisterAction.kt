package com.auth.presentation.register

sealed interface RegisterAction {
    data object OnChangePasswordVisibility : RegisterAction
    data object OnRegister : RegisterAction
    data object OnBack : RegisterAction
}