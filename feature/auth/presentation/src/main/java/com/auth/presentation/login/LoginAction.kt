package com.auth.presentation.login

sealed interface LoginAction {
    data object OnChangePasswordVisibility : LoginAction
    data object OnLogin : LoginAction
    data object OnRegister : LoginAction
}