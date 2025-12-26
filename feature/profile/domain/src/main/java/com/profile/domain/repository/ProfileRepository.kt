package com.profile.domain.repository

import com.core.domain.model.response.Response
import com.core.domain.model.user.User
import com.core.domain.util.result.DataError
import com.core.domain.util.result.Result

interface ProfileRepository {
    suspend fun getUser(): Result<Response<User>, DataError>
}