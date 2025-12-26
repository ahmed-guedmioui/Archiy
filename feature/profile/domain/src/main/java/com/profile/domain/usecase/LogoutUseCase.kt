package com.profile.domain.usecase

import com.core.domain.service.session.SessionService

class LogoutUseCase(
    private val sessionService: SessionService
) {
    suspend operator fun invoke() {
        sessionService.clearSession()
    }
}