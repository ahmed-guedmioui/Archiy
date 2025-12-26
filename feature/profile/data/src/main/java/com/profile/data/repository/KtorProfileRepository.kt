package com.profile.data.repository

import com.core.data.client.http.HttpRoutes
import com.core.data.client.http.KtorHttpClient
import com.core.data.dto.response.ResponseDto
import com.core.data.dto.user.UserDto
import com.core.data.dto.user.toDomain
import com.core.domain.model.response.Response
import com.core.domain.model.user.User
import com.core.domain.model.user.dummyUser
import com.core.domain.util.result.DataError
import com.core.domain.util.result.Result
import com.core.domain.util.result.map
import com.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.delay

class KtorProfileRepository(
    private val httpClient: KtorHttpClient
) : ProfileRepository {
    override suspend fun getUser(): Result<Response<User>, DataError> {
        // Simulate network delay
        delay(1000)

        // Replace this with a real register request
        return Result.Success(
            Response(
                message = "",
                data = dummyUser
            )
        )

        return httpClient.get<ResponseDto<UserDto>>(
            route = HttpRoutes.PROFILE
        ).map {
            it.toDomain()
        }
    }
}