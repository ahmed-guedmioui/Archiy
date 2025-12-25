package com.auth.domain.usecase

import com.auth.domain.service.email_validator.EmailValidatorService
import com.auth.domain.model.LoginData
import com.auth.domain.repository.AuthRepository
import com.core.domain.model.response.Response
import com.core.domain.service.session.SessionService
import com.core.domain.util.result.Error
import com.core.domain.util.result.Result
import com.core.domain.util.result.onSuccess

class RegisterUseCase(
    private val emailValidatorService: EmailValidatorService,
    private val authRepository: AuthRepository,
    private val sessionService: SessionService
) {

    sealed class RegisterError() : Error {
        data object EmptyUsername : RegisterError()
        data object InvalidEmail : RegisterError()
        data object EmptyPassword : RegisterError()
        data object MissingUserData : RegisterError()
    }

    suspend operator fun invoke(
        username: String = "",
        email: String = "",
        password: String = ""
    ): Result<Response<LoginData>, Error> {

        if (username.isEmpty()) {
            return Result.Error(RegisterError.EmptyUsername)
        }

        if (!emailValidatorService.isValid(email)) {
            return Result.Error(RegisterError.InvalidEmail)
        }

        if (password.isEmpty()) {
            return Result.Error(RegisterError.EmptyPassword)
        }

        val result = authRepository.register(username, email, password)

        return result.onSuccess { loginResponse ->
            val user = loginResponse.data?.user
            val token = loginResponse.data?.token
            if (user != null && token != null) {
                sessionService.saveSession(token, user)
                Result.Success(loginResponse)
            } else {
                sessionService.clearSession()
                Result.Error(RegisterError.MissingUserData)
            }
        }
    }
}