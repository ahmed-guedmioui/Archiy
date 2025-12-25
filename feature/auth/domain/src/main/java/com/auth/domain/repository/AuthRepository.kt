package com.auth.domain.repository

import com.auth.domain.model.LoginData
import com.core.domain.model.response.Response
import com.core.domain.util.result.DataError
import com.core.domain.util.result.Result

interface AuthRepository {
    suspend fun login(
        email: String,
        password: String
    ): Result<Response<LoginData>, DataError.Remote>

    suspend fun register(
        username: String,
        email: String,
        password: String
    ): Result<Response<LoginData>, DataError.Remote>
}