package com.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import com.app.di.appModule
import com.core.data.di.coreDataModule
import com.core.data.util.AndroidLoggingStrategy
import com.core.domain.util.Logger
import com.google.firebase.Firebase
import com.google.firebase.initialize
import com.auth.data.di.authDataModule
import com.auth.presentation.di.authPresentationModule
import com.core.presentation.di.corePresentationModule
import com.home.data.di.homeDataModule
import com.home.presentation.di.homePresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin

class App : Application(), KoinComponent {

    companion object {
        const val MESSAGE_FOR_DRIVER_NOTIFICATION_CHANNEL = "messages_for_driver"
        const val LOCATION_NOTIFICATION_CHANNEL = "location_notification_channel"
    }

    override fun onCreate() {
        super.onCreate()

        Firebase.initialize(applicationContext)

        createNotificationChannel(
            channelId = MESSAGE_FOR_DRIVER_NOTIFICATION_CHANNEL,
            notificationName = getString(R.string.messages_for_driver)
        )

        createLocationNotificationChannel()

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

    private fun createNotificationChannel(
        channelId: String,
        notificationName: String
    ) {
        val channel = NotificationChannel(
            channelId,
            notificationName,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            enableVibration(true)
        }
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
    }

    private fun createLocationNotificationChannel() {
        val channel = NotificationChannel(
            LOCATION_NOTIFICATION_CHANNEL,
            getString(R.string.location_notification),
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            setSound(null, null)
            enableVibration(false)
        }

        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }
}