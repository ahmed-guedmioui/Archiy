package com.nav_root.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import com.auth.presentation.AuthNavigation
import com.core.presentation.design_system.dialogs.AlertDialogSender
import com.core.presentation.design_system.dialogs.AlertDialogType
import com.core.presentation.design_system.dialogs.ErrorDialog
import com.core.presentation.design_system.dialogs.InfoDialog
import com.core.presentation.design_system.dialogs.WarningDialog
import com.core.presentation.util.ObserveAsEvent
import com.nav_root.api.NavDisplay

@Composable
fun NavRoot(
    modifier: Modifier = Modifier,
    isLoggedIn: Boolean
) {

    val backStack = rememberNavBackStack(
        if (isLoggedIn) Route.Main else Route.Auth
    )

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        entryProvider = entryProvider {
            entry<Route.Auth> {
                AuthNavigation(
                    onLoggedInOrRegistered = {
                        backStack.remove(Route.Auth)
                        backStack.add(Route.Main)
                    }
                )
            }

            entry<Route.Main> {
                MainBottomNavBar(
                    onLogout = {
                        backStack.remove(Route.Main)
                        backStack.add(Route.Auth)
                    }
                )
            }
        }
    )

    AlertDialog()
}

@Composable
private fun AlertDialog() {
    var message: String? by remember { mutableStateOf(null) }
    var type: AlertDialogType? by remember { mutableStateOf(null) }

    ObserveAsEvent(AlertDialogSender.alertDialogEvent) { dialogEvent ->
        type = dialogEvent.type
        message = dialogEvent.message
    }

    fun dismiss() {
        message = null
        type = null
    }

    if (message != null) {
        when (type) {
            AlertDialogType.ERROR -> {
                ErrorDialog(
                    errorMessage = message,
                    onDismiss = { dismiss() },
                    onPrimary = { dismiss() }
                )
            }

            AlertDialogType.WARNING -> {
                WarningDialog(
                    errorMessage = message,
                    onDismiss = { dismiss() },
                    onPrimary = { dismiss() }
                )
            }

            AlertDialogType.INFO -> {
                InfoDialog(
                    errorMessage = message,
                    onDismiss = { dismiss() },
                    onPrimary = { dismiss() }
                )
            }

            null -> { dismiss() }
        }
    }
}