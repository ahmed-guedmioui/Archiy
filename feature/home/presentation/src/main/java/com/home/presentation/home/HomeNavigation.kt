package com.home.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import com.home.presentation.detail.ItemDetailScreenRoot
import com.nav_root.api.NavDisplay

@Composable
fun HomeNavigation(
    modifier: Modifier = Modifier
) {
    val backStack = rememberNavBackStack(HomeRoute.List)

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        entryProvider = entryProvider {
            entry<HomeRoute.List> {
                HomeScreenRoot(
                    onNavigateToDetail = { itemId ->
                        backStack.add(HomeRoute.Detail(itemId))
                    }
                )
            }

            entry<HomeRoute.Detail> { route ->
                ItemDetailScreenRoot(
                    itemId = route.itemId,
                    onBack = {
                        backStack.removeLastOrNull()
                    }
                )
            }
        }
    )
}