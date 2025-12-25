package com.auth.presentation.di

import com.auth.domain.usecase.CheckCanLoginUseCase
import com.auth.domain.usecase.CheckCanRegisterUseCase
import com.auth.domain.usecase.LoginUseCase
import com.auth.domain.usecase.RegisterUseCase
import com.auth.presentation.login.LoginViewModel
import com.auth.presentation.register.RegisterViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authPresentationModule = module {
    factoryOf(::CheckCanRegisterUseCase)
    factoryOf(::CheckCanLoginUseCase)
    factoryOf(::RegisterUseCase)
    factoryOf(::LoginUseCase)

    viewModelOf(::RegisterViewModel)
    viewModelOf(::LoginViewModel)
}