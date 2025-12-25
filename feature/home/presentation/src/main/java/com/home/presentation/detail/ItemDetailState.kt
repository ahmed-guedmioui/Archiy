package com.home.presentation.detail

import com.home.domain.model.HomeItem

data class ItemDetailState(
    val item: HomeItem? = null,
    val isLoading: Boolean = false
)
