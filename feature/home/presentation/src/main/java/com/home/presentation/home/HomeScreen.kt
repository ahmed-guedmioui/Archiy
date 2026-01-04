package com.home.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.core.presentation.design_system.CoreScaffold
import com.core.presentation.design_system.CoreTopBar
import com.core.presentation.design_system.dialogs.AlertDialog
import com.core.presentation.design_system.dialogs.AlertDialogType
import com.core.presentation.extensions.animatedAppearance
import com.core.presentation.theme.theme.ArchiyTheme
import com.core.presentation.util.ObserveAsEvent
import com.home.domain.model.HomeItem
import com.home.domain.model.dummyHomeItems
import com.home.presentation.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreenRoot(
    viewModel: HomeViewModel = koinViewModel(),
    onNavigateToDetail: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvent(viewModel.event) { event ->
        when (event) {
            is HomeEvent.OnError -> {
                AlertDialog.show(
                    AlertDialogType.ERROR, event.error.asString(context)
                )
            }

            is HomeEvent.OnErrorText -> {
                AlertDialog.show(
                    AlertDialogType.ERROR, event.errorText
                )
            }
        }
    }

    HomeScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is HomeAction.OnItemClick -> onNavigateToDetail(action.itemId)
                else -> viewModel.onAction(action)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeState,
    onAction: (HomeAction) -> Unit
) {
    CoreScaffold(
        topBar = { scrollBehavior ->
            CoreTopBar(
                scrollBehavior = scrollBehavior,
                titleText = stringResource(R.string.home)
            )
        }
    ) { paddingValues ->
        if (state.isLoading && state.items.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .animatedAppearance()
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(
                    top = paddingValues.calculateTopPadding() + 16.dp,
                    bottom = paddingValues.calculateBottomPadding()
                )
            ) {
                if (state.items.isEmpty() && !state.isLoading) {
                    item {
                        Text(
                            text = stringResource(R.string.no_items_found),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                } else {
                    items(state.items) { item ->
                        HomeItemCard(
                            item = item,
                            onAction = onAction,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HomeItemCard(
    item: HomeItem,
    onAction: (HomeAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onAction(HomeAction.OnItemClick(item.id))
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiary
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onTertiary
            )

            Text(
                text = item.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onTertiary.copy(alpha = 0.7f),
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ArchiyTheme {
        HomeScreen(
            state = HomeState(
                items = dummyHomeItems
            ),
            onAction = {}
        )
    }
}