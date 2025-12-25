package com.home.domain.usecase

import com.core.domain.model.response.Response
import com.core.domain.util.result.Error
import com.core.domain.util.result.Result
import com.home.domain.model.HomeItem
import com.home.domain.repository.home.HomeRepository

class GetHomeItemsUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(): Result<Response<List<HomeItem>>, Error> {
        return homeRepository.getItems()
    }
}
