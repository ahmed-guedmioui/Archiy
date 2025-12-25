package com.home.domain.repository.home

import com.core.domain.model.response.Response
import com.core.domain.util.result.DataError
import com.core.domain.util.result.Result
import com.home.domain.model.HomeItem

interface HomeRepository {
    suspend fun getItems(): Result<Response<List<HomeItem>>, DataError.Remote>

    suspend fun getItemById(id: String): Result<Response<HomeItem>, DataError.Remote>
}
