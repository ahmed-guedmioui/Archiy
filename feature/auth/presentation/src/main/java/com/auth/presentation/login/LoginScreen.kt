package com.auth.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.auth.presentation.R
import com.core.presentation.design_system.CoreButton
import com.core.presentation.design_system.CorePasswordTextField
import com.core.presentation.design_system.CoreScaffold
import com.core.presentation.design_system.CoreTextField
import com.core.presentation.design_system.CoreTopBar
import com.core.presentation.design_system.dialogs.ErrorDialog
import com.core.presentation.theme.theme.ArchiyTheme
import com.core.presentation.theme.theme.Preview
import com.core.presentation.util.ObserveAsEvent
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreenRoot(
    viewModel: LoginViewModel = koinViewModel(),
    onRegister: () -> Unit,
    onLoggedIn: () -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    var errorMessage: String? by remember { mutableStateOf(null) }

    ObserveAsEvent(viewModel.event) { event ->
        when (event) {
            LoginEvent.OnLoggedIn -> {
                onLoggedIn()
            }

            is LoginEvent.OnError -> {
                errorMessage = event.error.asString(context)
            }
        }
    }

    errorMessage?.let { message ->
        val dismiss = { errorMessage = null }
        ErrorDialog(
            errorMessage = message, onDismiss = dismiss, onPrimary = dismiss
        )
    }

    LoginScreen(
        state = state,
        onAction = { action ->
            when (action) {
                LoginAction.OnRegister -> onRegister()
                else -> viewModel.onAction(action)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    state: LoginState,
    onAction: (LoginAction) -> Unit
) {
    CoreScaffold(
        topBar = {
            CoreTopBar(
                titleText = stringResource(R.string.login),
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(com.core.presentation.R.drawable.logo),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.weight(0.3f)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                CoreTextField(
                    textFieldState = state.email,
                    startIcon = Icons.Outlined.Email,
                    title = stringResource(R.string.email),
                    hint = stringResource(R.string.example_email_com),
                    keyBoardType = KeyboardType.Email
                )

                Spacer(Modifier.height(32.dp))

                CorePasswordTextField(
                    textFieldState = state.password,
                    title = stringResource(R.string.password),
                    hint = stringResource(R.string.enter_a_password),
                    isPasswordVisible = state.isPasswordVisible,
                    onTogglePasswordVisibility = {
                        onAction(LoginAction.OnChangePasswordVisibility)
                    }
                )

                Spacer(Modifier.height(32.dp))

                CoreButton(
                    text = stringResource(R.string.login),
                    isLoading = state.isLoggingIn,
                    enabled = state.canLogin,
                    onClick = {
                        onAction(LoginAction.OnLogin)
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(48.dp))

                Text(
                    text = stringResource(R.string.don_t_have_an_account),
                    textAlign = TextAlign.Center
                )

                TextButton(
                    onClick = {
                        onAction(LoginAction.OnRegister)
                    },
                    enabled = !state.isLoggingIn,
                ) {
                    Text(
                        text = stringResource(R.string.register),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ArchiyTheme {
        LoginScreen(
            state = LoginState(),
            onAction = {}
        )
    }
}