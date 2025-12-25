package com.home.presentation.home

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface HomeRoute : NavKey {
    @Serializable
    data object List : HomeRoute

    @Serializable
    data class Detail(val itemId: String) : HomeRoute
}
