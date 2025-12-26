package com.profile.di

import com.profile.domain.usecase.GetUserUseCase
import com.profile.domain.usecase.LogoutUseCase
import com.profile.presentation.ProfileViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val profilePresentationModule = module {
    factoryOf(::GetUserUseCase)
    factoryOf(::LogoutUseCase)

    viewModelOf(::ProfileViewModel)
}