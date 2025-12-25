package com.home.presentation.detail

sealed interface ItemDetailAction {
    data object OnBack : ItemDetailAction
    data class LoadItem(val itemId: String) : ItemDetailAction
}
