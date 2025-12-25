package com.auth.domain.usecase

import com.auth.domain.service.email_validator.EmailValidatorService
import com.auth.domain.model.LoginData
import com.auth.domain.repository.AuthRepository
import com.core.domain.model.response.Response
import com.core.domain.service.session.SessionService
import com.core.domain.util.result.Error
import com.core.domain.util.result.Result
import com.core.domain.util.result.onSuccess

class LoginUseCase(
    private val emailValidatorService: EmailValidatorService,
    private val authRepository: AuthRepository,
    private val sessionService: SessionService
) {

    sealed class LoginError() : Error {
        data object InvalidEmail : LoginError()
        data object EmptyPassword : LoginError()
        data object MissingUserData : LoginError()
    }

    suspend operator fun invoke(
        email: String = "",
        password: String = "",
    ): Result<Response<LoginData>, Error> {

        if (!emailValidatorService.isValid(email)) {
            return Result.Error(LoginError.InvalidEmail)
        }

        if (password.isEmpty()) {
            return Result.Error(LoginError.EmptyPassword)
        }

        val result = authRepository.login(email, password)

        return result.onSuccess { loginResponse ->
            val user = loginResponse.data?.user
            val token = loginResponse.data?.token
            if (user != null && token != null) {
                sessionService.saveSession(token, user)
                Result.Success(loginResponse)
            } else {
                sessionService.clearSession()
                Result.Error(LoginError.MissingUserData)
            }
        }
    }
}