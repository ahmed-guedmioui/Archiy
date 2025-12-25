package com.home.data.repository.home

import com.core.data.client.http.HttpRoutes
import com.core.data.client.http.KtorHttpClient
import com.core.data.dto.response.ResponseDto
import com.core.domain.model.response.Response
import com.core.domain.util.result.DataError
import com.core.domain.util.result.Result
import com.core.domain.util.result.map
import com.home.data.dto.HomeItemDto
import com.home.data.dto.toDomain
import com.home.domain.model.HomeItem
import com.home.domain.model.dummyHomeItems
import com.home.domain.repository.home.HomeRepository
import kotlinx.coroutines.delay

class KtorHomeRepository(
    private val httpClient: KtorHttpClient
) : HomeRepository {
    override suspend fun getItems(): Result<Response<List<HomeItem>>, DataError.Remote> {
        // Simulate network delay
        delay(1000)

        // Replace this with a real item list request
        return Result.Success(
            Response(
                message = "",
                data = dummyHomeItems.map { it }
            )
        )

        return httpClient.post<ResponseDto<List<HomeItemDto>>>(
            route = HttpRoutes.ITEM_LIST,
        ).map {
            Response(
                message = it.message.orEmpty(),
                data = it.data?.map { item -> item.toDomain() }
            )
        }
    }

    override suspend fun getItemById(
        id: String
    ): Result<Response<HomeItem>, DataError.Remote> {
        // Simulate network delay
        delay(1000)

        // Replace this with a real item detail request
        val item = dummyHomeItems.find { it.id == id }
        return Result.Success(
            Response(
                message = "",
                data = item
            )
        )


        return httpClient.post<ResponseDto<HomeItemDto>>(
            route = HttpRoutes.ITEM,
        ).map {
            it.toDomain()
        }
    }
}