package com.nav_root.impl

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import com.auth.presentation.AuthNavigation
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
}