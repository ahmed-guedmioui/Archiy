package com.core.domain.model.response

data class Response<T>(
    val message: String,
    val data: T?
)