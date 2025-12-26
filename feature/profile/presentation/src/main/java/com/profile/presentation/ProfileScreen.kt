package com.profile.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.core.domain.model.user.dummyUser
import com.core.presentation.design_system.CoreOutlinedButton
import com.core.presentation.design_system.CoreScaffold
import com.core.presentation.design_system.CoreTopBar
import com.core.presentation.design_system.dialogs.ErrorDialog
import com.core.presentation.theme.theme.ArchiyTheme
import com.core.presentation.util.ObserveAsEvent
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreenRoot(
    viewModel: ProfileViewModel = koinViewModel(),
    onLogout: () -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    var errorMessage: String? by remember { mutableStateOf(null) }

    ObserveAsEvent(viewModel.event) { event ->
        when (event) {
            ProfileEvent.Logout -> onLogout()

            is ProfileEvent.OnError -> {
                errorMessage = event.error.asString(context)
            }

            is ProfileEvent.OnErrorText -> {
                errorMessage = event.errorText
            }
        }
    }

    errorMessage?.let { message ->
        val dismiss = { errorMessage = null }
        ErrorDialog(
            errorMessage = message, onDismiss = dismiss, onPrimary = dismiss
        )
    }

    ProfileScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    state: ProfileState,
    onAction: (ProfileAction) -> Unit
) {
    CoreScaffold(
        topBar = {
            CoreTopBar(
                titleText = stringResource(R.string.profile)
            )
        }
    ) { paddingValues ->
        if (state.isLoading && state.user == null) {
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
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${stringResource(R.string.id)}:\n${state.user?.id}",
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "${stringResource(R.string.username)}:\n${state.user?.username}",
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "${stringResource(R.string.email)}:\n${state.user?.email}",
                    textAlign = TextAlign.Center
                )

                CoreOutlinedButton(
                    text = stringResource(R.string.logout),
                    onClick = {
                        onAction(ProfileAction.OnLogout)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ArchiyTheme {
        ProfileScreen(
            state = ProfileState(
                user = dummyUser
            ),
            onAction = {}
        )
    }
}