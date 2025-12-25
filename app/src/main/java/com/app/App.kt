package com.app

import android.app.Application
import com.app.di.appModule
import com.auth.data.di.authDataModule
import com.auth.presentation.di.authPresentationModule
import com.core.data.di.coreDataModule
import com.core.data.util.AndroidLoggingStrategy
import com.core.domain.util.Logger
import com.core.presentation.di.corePresentationModule
import com.google.firebase.Firebase
import com.google.firebase.initialize
import com.home.data.di.homeDataModule
import com.home.presentation.di.homePresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin

class App : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()

        Firebase.initialize(applicationContext)

        startKoin {
            androidContext(this@App)
            modules(
                appModule,
                coreDataModule,
                corePresentationModule,
                authDataModule,
                authPresentationModule,
                homeDataModule,
                homePresentationModule,
            )
        }

        Logger.setLoggingStrategy(AndroidLoggingStrategy())
    }
}