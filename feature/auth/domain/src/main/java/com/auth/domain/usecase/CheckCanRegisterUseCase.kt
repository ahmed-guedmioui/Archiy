package com.auth.domain.usecase

class CheckCanRegisterUseCase {
    operator fun invoke(
        username: String,
        email: String,
        password: String,
        isLoggingIn: Boolean = false
    ): Boolean {
        return username.isNotEmpty()
                && email.isNotEmpty()
                && password.isNotEmpty()
                && !isLoggingIn
    }
}