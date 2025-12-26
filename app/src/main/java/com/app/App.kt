package com.app

import android.app.Application
import com.app.di.appModule
import com.auth.data.di.authDataModule
import com.auth.presentation.di.authPresentationModule
import com.core.data.di.coreDataModule
import com.core.data.util.AndroidLoggingStrategy
import com.core.domain.util.LoggerBuilder
import com.core.presentation.di.corePresentationModule
import com.google.firebase.Firebase
import com.google.firebase.initialize
import com.home.data.di.homeDataModule
import com.home.presentation.di.homePresentationModule
import com.profile.di.profilePresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Firebase.initialize(applicationContext)

        LoggerBuilder.setLoggingStrategy(AndroidLoggingStrategy())

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
                profilePresentationModule
            )
        }
    }
}