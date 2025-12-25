package com.core.data.di

import com.core.data.client.http.KtorHttpClient
import com.core.data.client.http.KtorHttpClientFactory
import com.core.data.client.local_storage.DataStoreLocalStorageClient
import com.core.data.client.local_storage.datastore.DATASTORE_PREFERENCES_KEY
import com.core.data.client.local_storage.datastore.DataStoreFactory
import com.core.data.service.session.LocalStorageSessionService
import com.core.domain.client.local_storage.LocalStorageClient
import com.core.domain.service.session.SessionService
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDataModule = module {
    single {
        KtorHttpClientFactory.build(OkHttp.create(), get())
    }

    singleOf(::KtorHttpClient)

    single {
        DataStoreLocalStorageClient(
            DataStoreFactory.create(androidApplication(), DATASTORE_PREFERENCES_KEY)
        )
    }.bind<LocalStorageClient>()

    singleOf(::LocalStorageSessionService).bind<SessionService>()
}