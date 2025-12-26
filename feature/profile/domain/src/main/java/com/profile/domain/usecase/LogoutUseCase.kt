package com.profile.domain.usecase

import com.core.domain.service.session.SessionService
import com.profile.domain.repository.ProfileRepository

class LogoutUseCase(
    private val profileRepository: ProfileRepository,
    private val sessionService: SessionService
) {
    suspend operator fun invoke() {
        sessionService.clearSession()
    }
}