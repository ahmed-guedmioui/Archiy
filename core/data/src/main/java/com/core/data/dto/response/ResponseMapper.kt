package com.core.data.dto.response

import com.core.domain.model.response.Response

fun <T> ResponseDto<T>.toDomain(data: T? = null): Response<T> = Response(
    message = message.orEmpty(),
    data = data
)

fun <T> Response<T>.toDto(data: T? = null): ResponseDto<T> = ResponseDto(
    message = message,
    data = data
)