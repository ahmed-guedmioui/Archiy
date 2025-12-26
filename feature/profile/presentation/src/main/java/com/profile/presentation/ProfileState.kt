package com.profile.presentation

import com.core.domain.model.user.User

data class ProfileState(
    val isLoading: Boolean = false,
    val user: User? = null
)