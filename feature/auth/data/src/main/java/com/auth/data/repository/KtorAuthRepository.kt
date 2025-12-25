package com.auth.data.repository

import com.auth.data.dto.LoginDataDto
import com.auth.data.dto.toDomain
import com.auth.domain.model.LoginData
import com.auth.domain.repository.AuthRepository
import com.core.data.client.http.HttpRoutes
import com.core.data.client.http.KtorHttpClient
import com.core.data.dto.response.ResponseDto
import com.core.data.dto.user.UserDto
import com.core.domain.model.response.Response
import com.core.domain.util.result.DataError
import com.core.domain.util.result.Result
import com.core.domain.util.result.map
import kotlinx.coroutines.delay

class KtorAuthRepository(
    private val httpClient: KtorHttpClient
) : AuthRepository {

    override suspend fun login(
        email: String,
        password: String
    ): Result<Response<LoginData>, DataError.Remote> {
        // Simulate network delay
        delay(1000)

        // Replace this with a real login request
        return Result.Success(
            ResponseDto(
                data = LoginDataDto(
                    token = "some_token",
                    user = UserDto(
                        id = "marry_id",
                        username = "Marry",
                        email = email
                    )
                )
            ).toDomain()
        )

        return httpClient.post<ResponseDto<LoginDataDto>>(
            route = HttpRoutes.LOGIN,
            body = mapOf(
                "email" to email,
                "password" to password
            )
        ).map {
            it.toDomain()
        }
    }

    override suspend fun register(
        username: String,
        email: String,
        password: String
    ): Result<Response<LoginData>, DataError.Remote> {
        // Simulate network delay
        delay(1000)

        // Replace this with a real register request
        return Result.Success(
            ResponseDto(
                data = LoginDataDto(
                    token = "some_token",
                    user = UserDto(
                        id = "${username}_id",
                        username = username,
                        email = email
                    )
                )
            ).toDomain()
        )

        return httpClient.post<ResponseDto<LoginDataDto>>(
            route = HttpRoutes.REGISTER,
            body = mapOf(
                "username" to username,
                "email" to email,
                "password" to password
            )
        ).map {
            it.toDomain()
        }
    }
}