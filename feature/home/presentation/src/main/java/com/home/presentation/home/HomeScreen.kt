package com.home.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.core.presentation.design_system.dialogs.ErrorDialog
import com.core.presentation.theme.theme.CoreTheme
import com.core.presentation.util.ObserveAsEvent
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreenRoot(
    viewModel: HomeViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    var errorMessage: String? by remember { mutableStateOf(null) }

    ObserveAsEvent(viewModel.event) { event ->
        errorMessage = when (event) {
            is HomeEvent.OnError -> {
                event.error.asString(context)
            }

            is HomeEvent.OnErrorText -> {
                event.errorText
            }
        }
    }

    errorMessage?.let { message ->
        val dismiss = { errorMessage = null }
        ErrorDialog(
            errorMessage = message, onDismiss = dismiss, onPrimary = dismiss
        )
    }

    HomeScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun HomeScreen(
    state: HomeState,
    onAction: (HomeAction) -> Unit
) {

}

@Preview
@Composable
private fun Preview() {
    CoreTheme {
        HomeScreen(
            state = HomeState(),
            onAction = {}
        )
    }
}