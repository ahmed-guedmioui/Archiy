package com.app.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.core.presentation.theme.theme.ArchiyTheme
import com.nav_root.impl.NavRoot
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinComponent

class MainActivity : ComponentActivity(), KoinComponent {

    private val viewModel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            SystemBarStyle.light(
                scrim = Color.Transparent.toArgb(), darkScrim = Color.Transparent.toArgb()
            )
        )

        var isCheckingLogin: Boolean? = null

        installSplashScreen().setKeepOnScreenCondition {
            isCheckingLogin == true
        }

        setContent {
            ArchiyTheme {
                val state by viewModel.state.collectAsStateWithLifecycle()
                isCheckingLogin = state.isCheckingLogin

                state.isLoggedIn?.let { isLoggedIn ->
                    NavRoot(
                        isLoggedIn = isLoggedIn,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                    )
                }
            }
        }
    }
}