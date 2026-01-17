package com.vitocuaderno.gweather.presentation.login

import androidx.lifecycle.viewModelScope
import com.vitocuaderno.gweather.core.exceptions.Either
import com.vitocuaderno.gweather.domain.usecase.user.LoginUserUseCase
import com.vitocuaderno.gweather.presentation.base.BaseViewModel
import com.vitocuaderno.gweather.presentation.navigation.NavigationEvent
import com.vitocuaderno.gweather.presentation.navigation.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
    @Inject
    constructor(
        private val loginUserUseCase: LoginUserUseCase,
    ) : BaseViewModel() {
        private val _uiState = MutableStateFlow(LoginUiState())
        val uiState: StateFlow<LoginUiState> = _uiState

        fun onEmailChanged(email: String) {
            _uiState.update { it.copy(email = email) }
        }

        fun onPasswordChanged(password: String) {
            _uiState.update { it.copy(password = password) }
        }

        fun login() {
            _uiState.update { it.copy(isLoading = true) }
            loginUserUseCase
                .run(LoginUserUseCase.Params(_uiState.value.email, _uiState.value.password))
                .onEach { result ->
                    when (result) {
                        is Either.Left -> _uiState.update { it.copy(isLoading = false, error = result.a) }
                        is Either.Right -> {
                            _uiState.update { it.copy(isLoading = false, isSuccess = true) }
                            navigate(NavigationEvent.PopAndTo(Screens.Dashboard, Screens.Login.route))
                        }
                    }
                }.launchIn(viewModelScope)
        }
    }

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: com.vitocuaderno.gweather.core.exceptions.Failure? = null,
)
