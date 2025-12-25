package com.core.data.dto.user

import com.core.domain.model.user.User

fun UserDto.toDomain() = User(
    username = username.orEmpty(),
    email = email.orEmpty(),
    id = id.orEmpty()
)

fun User.toDto() = UserDto(
    username = username,
    email = email,
    id = id
)