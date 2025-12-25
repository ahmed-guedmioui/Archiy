package com.home.presentation.home

import com.home.domain.model.HomeItem

data class HomeState(
    val isLoading: Boolean = false,
    val items: List<HomeItem> = emptyList()
)