package com.nav_root.impl

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.core.presentation.design_system.BottomNavigationItem
import com.core.presentation.design_system.CoreBottomBar
import com.home.presentation.home.HomeNavigation


@Composable
fun <T : Any> MainBottomNavBar(
    backStack: List<T>
) {

    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }

    val bottomBarItems = remember {
        mutableListOf(
            BottomNavigationItem(
                selectedIcon = Icons.Default.Home,
                unselectedIcon = Icons.Outlined.Home
            ),
            BottomNavigationItem(
                selectedIcon = Icons.Default.Person,
                unselectedIcon = Icons.Outlined.Person
            )
        )
    }

    fun onItemClick(index: Int) {
        selectedItem = index
    }

    Scaffold(
        bottomBar = {
            CoreBottomBar(
                items = bottomBarItems,
                selectedItem = selectedItem,
                onItemClick = { index ->
                    onItemClick(index)
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding())
        ) {
            AnimatedVisibility(
                visible = selectedItem == 0,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                BackHandler {
                    onItemClick(0)
                }

                HomeNavigation()
            }

            AnimatedVisibility(
                visible = selectedItem == 1,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                BackHandler {
                    onItemClick(0)
                }
            }
        }
    }
}