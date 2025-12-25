package com.auth.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import com.auth.presentation.login.LoginScreenRoot
import com.auth.presentation.register.RegisterScreenRoot
import com.nav_root.api.NavDisplay

@Composable
fun AuthNavigation(
    modifier: Modifier = Modifier,
    onLoggedInOrRegistered: () -> Unit
) {

    val backStack = rememberNavBackStack(AuthRoute.Login)

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        entryProvider = entryProvider {
            entry<AuthRoute.Login> {
                LoginScreenRoot(
                    onRegister = {
                        backStack.add(AuthRoute.Register)
                    },
                    onLoggedIn = {
                        onLoggedInOrRegistered()
                    }
                )
            }

            entry<AuthRoute.Register> {
                RegisterScreenRoot(
                    onBack = {
                        backStack.removeLastOrNull()
                    },
                    onRegistered = {
                        onLoggedInOrRegistered()
                    }
                )
            }
        }
    )
}