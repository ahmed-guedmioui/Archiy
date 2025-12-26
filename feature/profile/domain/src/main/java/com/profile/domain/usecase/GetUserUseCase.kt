package com.profile.domain.usecase

import com.core.domain.model.user.User
import com.core.domain.service.session.SessionService

class GetUserUseCase(
    private val sessionService: SessionService
) {
    suspend operator fun invoke(): User? {
        return sessionService.getUser()
    }
}