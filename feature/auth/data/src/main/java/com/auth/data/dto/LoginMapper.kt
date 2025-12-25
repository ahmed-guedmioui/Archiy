package com.auth.data.dto

import com.auth.domain.model.LoginData
import com.core.data.dto.response.ResponseDto
import com.core.data.dto.user.toDomain
import com.core.data.dto.user.toDto
import com.core.domain.model.response.Response

fun ResponseDto<LoginDataDto>.toDomain(): Response<LoginData> = Response(
    message = message.orEmpty(),
    data = data?.toDomain()
)


fun LoginDataDto.toDomain() = LoginData(
    token = token ?: "",
    user = user?.toDomain()
)

fun LoginData.toDto() = LoginDataDto(
    token = token,
    user = user?.toDto()
)