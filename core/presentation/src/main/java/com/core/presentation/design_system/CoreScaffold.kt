package com.core.presentation.design_system

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoreScaffold(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        rememberTopAppBarState()
    ),
    withScrollBehavior: Boolean = true,
    withPullToRefresh: Boolean = true,
    withBottomPadding: Boolean = true,
    containerColor: Color = MaterialTheme.colorScheme.background,
    showTopBarHorizontalDivider: Boolean = false,
    snackbarHost: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    topBar: @Composable (TopAppBarScrollBehavior) -> Unit = {},
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    bottomBar: @Composable () -> Unit = {},
    onPullToRefresh: () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {

    val scrollModifier =
        if (withScrollBehavior) modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
        else modifier

    if (withPullToRefresh) {
        val refreshScope = rememberCoroutineScope()
        var refreshing by remember { mutableStateOf(false) }

        fun refresh() = refreshScope.launch {
            refreshing = true
            delay(200)
            onPullToRefresh()
            delay(200)
            refreshing = false
        }

        val state = rememberPullToRefreshState()
        Scaffold(
            containerColor = containerColor,
            contentWindowInsets = contentWindowInsets,
            snackbarHost = snackbarHost,
            modifier = scrollModifier,
            topBar = {
                Column {
                    topBar(scrollBehavior)
                    if (showTopBarHorizontalDivider) {
                        HorizontalDivider(Modifier.alpha(0.4f))
                    }
                }
            },
            bottomBar = bottomBar,
            floatingActionButton = floatingActionButton,
            floatingActionButtonPosition = floatingActionButtonPosition
        ) { padding ->
            PullToRefreshBox(
                modifier = Modifier
                    .padding(top = padding.calculateTopPadding())
                    .padding(bottom = if (withBottomPadding) padding.calculateBottomPadding() else 0.dp),
                isRefreshing = refreshing,
                state = state,
                onRefresh = ::refresh,
                indicator = {
                    Column(
                        modifier = Modifier.align(Alignment.TopCenter)
                    ) {
                        Indicator(
                            modifier = Modifier
                                .size(35.dp),
                            isRefreshing = refreshing,
                            state = state,
                            containerColor = MaterialTheme.colorScheme.background,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                }
            ) {
                content(PaddingValues())
            }
        }
    } else {
        Scaffold(
            containerColor = containerColor,
            contentWindowInsets = contentWindowInsets,
            snackbarHost = snackbarHost,
            modifier = scrollModifier,
            topBar = {
                topBar(scrollBehavior)
                if (showTopBarHorizontalDivider) {
                    HorizontalDivider(Modifier.alpha(0.4f))
                }
            },
            bottomBar = bottomBar,
            floatingActionButton = floatingActionButton,
            floatingActionButtonPosition = floatingActionButtonPosition
        ) { padding ->
            content(padding)
        }
    }
}
