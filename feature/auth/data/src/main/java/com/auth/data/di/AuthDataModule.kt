package com.auth.data.di

import com.auth.data.service.email_validator.AndroidEmailValidatorService
import com.auth.data.repository.KtorAuthRepository
import com.auth.domain.service.email_validator.EmailValidatorService
import com.auth.domain.repository.AuthRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authDataModule = module {
    factoryOf(::AndroidEmailValidatorService).bind<EmailValidatorService>()
    factoryOf(::KtorAuthRepository).bind<AuthRepository>()
}