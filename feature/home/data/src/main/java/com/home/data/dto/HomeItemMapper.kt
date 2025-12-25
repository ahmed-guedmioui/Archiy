package com.home.data.dto

import com.core.data.dto.response.ResponseDto
import com.core.domain.model.response.Response
import com.home.domain.model.HomeItem

fun ResponseDto<HomeItemDto>.toDomain(): Response<HomeItem> = Response(
    message = message.orEmpty(),
    data = data?.toDomain()
)

fun HomeItemDto.toDomain() = HomeItem(
    id = id.orEmpty(),
    title = title.orEmpty(),
    description = description.orEmpty()
)

fun HomeItem.toDto() = HomeItemDto(
    id = id,
    title = title,
    description = description
)