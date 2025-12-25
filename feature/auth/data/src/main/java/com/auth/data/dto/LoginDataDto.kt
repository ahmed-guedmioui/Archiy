package com.auth.data.dto

import com.core.data.dto.user.UserDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginDataDto(
    @SerialName("token") val token: String? = null,
    @SerialName("user") val user: UserDto? = null
)