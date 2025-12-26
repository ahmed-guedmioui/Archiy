package com.profile.presentation

sealed interface ProfileAction {
    data object OnLogout : ProfileAction
}