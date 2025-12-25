package com.home.data.di

import com.home.data.repository.home.KtorHomeRepository
import com.home.domain.repository.home.HomeRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val homeDataModule = module {
    factoryOf(::KtorHomeRepository).bind<HomeRepository>()
}