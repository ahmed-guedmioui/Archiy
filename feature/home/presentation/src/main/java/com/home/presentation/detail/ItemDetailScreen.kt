package com.home.presentation.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.core.presentation.design_system.CoreScaffold
import com.core.presentation.design_system.CoreTopBar
import com.core.presentation.design_system.dialogs.AlertDialog
import com.core.presentation.design_system.dialogs.AlertDialogType
import com.core.presentation.extensions.animatedAppearance
import com.core.presentation.util.ObserveAsEvent
import com.home.presentation.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun ItemDetailScreenRoot(
    viewModel: ItemDetailViewModel = koinViewModel(),
    itemId: String,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvent(viewModel.event) { event ->
        when (event) {
            is ItemDetailEvent.OnError -> {
                AlertDialog.show(
                    AlertDialogType.ERROR, event.error.asString(context)
                )
            }

            is ItemDetailEvent.OnErrorText -> {
                AlertDialog.show(
                    AlertDialogType.ERROR, event.errorText
                )
            }
        }
    }

    ItemDetailScreen(
        state = state,
        onAction = { action ->
            when (action) {
                ItemDetailAction.OnBack -> onBack()
                else -> viewModel.onAction(action)
            }
        }
    )

    // Load item when screen is first shown
    LaunchedEffect(itemId) {
        viewModel.onAction(ItemDetailAction.LoadItem(itemId))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailScreen(
    state: ItemDetailState,
    onAction: (ItemDetailAction) -> Unit
) {
    CoreScaffold(
        topBar = {
            CoreTopBar(
                titleText = stringResource(R.string.item_details),
                navigationIcon = Icons.Default.ArrowBackIosNew,
                onNavigationClick = {
                    onAction(ItemDetailAction.OnBack)
                }
            )
        }
    ) { paddingValues ->
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .animatedAppearance()
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                state.item?.let { item ->
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )

                    Text(
                        text = item.description,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )
                } ?: run {
                    Text(
                        text = stringResource(R.string.item_not_found),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}