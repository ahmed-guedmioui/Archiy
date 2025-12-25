package com.home.presentation.di

import com.home.domain.usecase.GetHomeItemByIdUseCase
import com.home.domain.usecase.GetHomeItemsUseCase
import com.home.presentation.detail.ItemDetailViewModel
import com.home.presentation.home.HomeViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val homePresentationModule = module {
    factoryOf(::GetHomeItemsUseCase)
    factoryOf(::GetHomeItemByIdUseCase)

    viewModelOf(::HomeViewModel)
    viewModelOf(::ItemDetailViewModel)
}