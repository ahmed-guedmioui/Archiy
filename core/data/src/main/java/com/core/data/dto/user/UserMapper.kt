package com.core.data.dto.user

import com.core.data.dto.response.ResponseDto
import com.core.domain.model.response.Response
import com.core.domain.model.user.User

fun ResponseDto<UserDto>.toDomain(): Response<User> = Response(
    message = message.orEmpty(),
    data = data?.toDomain()
)


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