package com.home.domain.usecase

import com.core.domain.model.response.Response
import com.core.domain.util.result.Error
import com.core.domain.util.result.Result
import com.home.domain.model.HomeItem
import com.home.domain.repository.home.HomeRepository

class GetHomeItemByIdUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(id: String): Result<Response<HomeItem>, Error> {
        return homeRepository.getItemById(id)
    }
}
