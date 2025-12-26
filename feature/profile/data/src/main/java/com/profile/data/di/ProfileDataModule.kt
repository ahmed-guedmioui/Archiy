package com.profile.data.di

import com.profile.data.repository.KtorProfileRepository
import com.profile.domain.repository.ProfileRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val profileDataModule = module {
    singleOf(::KtorProfileRepository).bind<ProfileRepository>()
}