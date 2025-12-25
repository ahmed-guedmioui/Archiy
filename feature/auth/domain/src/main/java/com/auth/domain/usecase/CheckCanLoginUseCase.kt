package com.auth.domain.usecase

class CheckCanLoginUseCase {
    operator fun invoke(
        email: String,
        password: String,
        isLoggingIn: Boolean = false
    ): Boolean {
        return email.isNotEmpty()
                && password.isNotEmpty()
                && !isLoggingIn
    }
}