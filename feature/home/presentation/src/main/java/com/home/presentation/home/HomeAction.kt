package com.home.presentation.home

sealed interface HomeAction {
    data object LoadItems : HomeAction
    data class OnItemClick(val itemId: String) : HomeAction
}