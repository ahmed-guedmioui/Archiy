package com.auth.domain.model

import com.core.domain.model.user.User

data class LoginData(
    val token: String,
    val user: User?
)