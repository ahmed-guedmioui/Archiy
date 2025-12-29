package com.auth.presentation.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.auth.presentation.R
import com.core.presentation.design_system.CoreButton
import com.core.presentation.design_system.CorePasswordTextField
import com.core.presentation.design_system.CoreScaffold
import com.core.presentation.design_system.CoreTextField
import com.core.presentation.design_system.CoreTopBar
import com.core.presentation.design_system.dialogs.AlertDialog
import com.core.presentation.design_system.dialogs.AlertDialogType
import com.core.presentation.theme.theme.ArchiyTheme
import com.core.presentation.theme.theme.Preview
import com.core.presentation.util.ObserveAsEvent
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreenRoot(
    viewModel: RegisterViewModel = koinViewModel(),
    onBack: () -> Unit,
    onRegistered: () -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvent(viewModel.event) { event ->
        when (event) {
            RegisterEvent.OnRegistered -> {
                onRegistered()
            }

            is RegisterEvent.OnError -> {
                AlertDialog.show(
                    AlertDialogType.ERROR, event.error.asString(context)
                )
            }
        }
    }

    RegisterScreen(
        state = state,
        onAction = { action ->
            when (action) {
                RegisterAction.OnBack -> onBack()
                else -> viewModel.onAction(action)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit
) {
    CoreScaffold(
        topBar = {
            CoreTopBar(
                titleText = stringResource(R.string.register),
                navigationIcon = Icons.Outlined.ArrowBackIosNew,
                onNavigationClick = {
                    onAction(RegisterAction.OnBack)
                }
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
                    textFieldState = state.username,
                    title = stringResource(R.string.username),
                    hint = stringResource(R.string.username_example),
                )

                Spacer(Modifier.height(16.dp))

                CoreTextField(
                    startIcon = Icons.Outlined.Email,
                    textFieldState = state.email,
                    title = stringResource(R.string.email),
                    hint = stringResource(R.string.example_email_com),
                    keyBoardType = KeyboardType.Email
                )

                Spacer(Modifier.height(16.dp))

                CorePasswordTextField(
                    textFieldState = state.password,
                    title = stringResource(R.string.password),
                    hint = stringResource(R.string.enter_a_password),
                    isPasswordVisible = state.isPasswordVisible,
                    onTogglePasswordVisibility = {
                        onAction(RegisterAction.OnChangePasswordVisibility)
                    }
                )

                Spacer(Modifier.height(32.dp))

                CoreButton(
                    text = stringResource(R.string.register),
                    isLoading = state.isRegistering,
                    enabled = state.canRegister,
                    onClick = {
                        onAction(RegisterAction.OnRegister)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ArchiyTheme {
        RegisterScreen(
            state = RegisterState(),
            onAction = {}
        )
    }
}