package com.core.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseDto<T>(
    @SerialName("message") val message: String? = null,
    @SerialName("data") val data: T? = null
)