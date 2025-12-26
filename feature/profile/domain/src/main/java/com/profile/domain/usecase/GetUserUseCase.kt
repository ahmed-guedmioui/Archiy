package com.profile.domain.usecase

import com.core.domain.model.response.Response
import com.core.domain.model.user.User
import com.core.domain.util.result.DataError
import com.core.domain.util.result.Result
import com.profile.domain.repository.ProfileRepository

class GetUserUseCase(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(): Result<Response<User>, DataError> {
        return profileRepository.getUser()
    }
}