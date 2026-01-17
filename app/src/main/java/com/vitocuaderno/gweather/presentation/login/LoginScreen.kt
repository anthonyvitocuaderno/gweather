package com.vitocuaderno.gweather.presentation.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vitocuaderno.gweather.presentation.navigation.NavigationEvent
import com.vitocuaderno.gweather.presentation.navigation.Navigator
import com.vitocuaderno.gweather.presentation.navigation.Screens
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navigator: Navigator,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = viewModel.navigation) {
        viewModel.navigation.collectLatest { nav ->
            when (nav) {
                is NavigationEvent.To -> navigator.navigate(nav.screen)
                is NavigationEvent.PopAndTo -> navigator.navigateAndClearBackStack(nav.screen, nav.popUpTo)
            }
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("GWeather") },
                // TopAppBar often handles status bar insets automatically
            )
        },
        modifier = Modifier.navigationBarsPadding(),
    ) { innerPadding ->

        LoginScreenContent(
            uiState = uiState,
            onEmailChanged = viewModel::onEmailChanged,
            onPasswordChanged = viewModel::onPasswordChanged,
            onLogin = viewModel::login,
            onSignUp = { navigator.navigate(Screens.Register) },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreenContent(
    uiState: LoginUiState,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onLogin: () -> Unit,
    onSignUp: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text("Sign In", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(32.dp))

            if (uiState.isLoading) {
                CircularProgressIndicator()
            } else {
                TextField(
                    value = uiState.email,
                    onValueChange = { onEmailChanged(it.filter { char -> !char.isWhitespace() }) },
                    label = { Text("Email") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    isError = uiState.error != null,
                    singleLine = true,
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = uiState.password,
                    onValueChange = { onPasswordChanged(it.filter { char -> !char.isWhitespace() }) },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    isError = uiState.error != null,
                    singleLine = true,
                )
                Spacer(modifier = Modifier.height(16.dp))

                uiState.error?.let {
                    Text(
                        text = "Login failed: $it",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(bottom = 8.dp),
                    )
                }

                Button(
                    onClick = onLogin,
                    enabled = uiState.email.isNotBlank() && uiState.password.isNotBlank(),
                ) {
                    Text("Continue")
                }
                TextButton(onClick = { /* TODO */ }) {
                    Text("Forgot your password?")
                }
                Spacer(modifier = Modifier.height(16.dp))

                ClickableText(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onSurface)) {
                            append("Don't have an account yet? ")
                        }
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                            append("Sign up")
                        }
                    },
                    onClick = { onSignUp() },
                )
            }

            if (uiState.isSuccess) {
                Text("Login successful!")
            }
        }
    }
}
