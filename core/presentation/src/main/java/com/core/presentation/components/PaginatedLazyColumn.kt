package com.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@Composable
fun PaginatedLazyColumn(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    loading: Boolean,
    listState: LazyListState = rememberLazyListState(),
    preLoadIndex: Int = 1,
    onPaginate: () -> Unit,
    content: LazyListScope.() -> Unit
) {
    val shouldPaginate = remember {
        derivedStateOf {
            val itemCount = listState.layoutInfo.totalItemsCount
            val lastVisibleIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0

            if ((itemCount - preLoadIndex) >= 0) {
                lastVisibleIndex >= itemCount - preLoadIndex
            } else {
                false
            }
        }
    }

    LaunchedEffect(key1 = listState, key2 = loading) {
        snapshotFlow { shouldPaginate.value }
            .distinctUntilChanged()
            .filter { it && !loading }
            .collect {
                onPaginate()
            }
    }

    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxSize(),
        verticalArrangement = verticalArrangement,
        contentPadding = contentPadding
    ) {
        content()

        if (loading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        strokeWidth = 1.dp,
                        color = MaterialTheme.colorScheme.onBackground.copy(0.8f),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}
