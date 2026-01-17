package com.vitocuaderno.gweather.presentation.register

import androidx.lifecycle.viewModelScope
import com.vitocuaderno.gweather.core.exceptions.Either
import com.vitocuaderno.gweather.core.exceptions.Failure
import com.vitocuaderno.gweather.domain.usecase.user.LoginUserUseCase
import com.vitocuaderno.gweather.domain.usecase.user.RegisterUserUseCase
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
class RegisterViewModel
    @Inject
    constructor(
        private val registerUserUseCase: RegisterUserUseCase,
        private val loginUserUseCase: LoginUserUseCase,
    ) : BaseViewModel() {
        private val _uiState = MutableStateFlow(RegisterUiState())
        val uiState: StateFlow<RegisterUiState> = _uiState

        fun onEmailChanged(email: String) {
            _uiState.update { it.copy(email = email) }
        }

        fun onPasswordChanged(password: String) {
            _uiState.update { it.copy(password = password) }
        }

        fun onConfirmPasswordChanged(password: String) {
            _uiState.update { it.copy(confirmPassword = password) }
        }

        fun register() {
            if (_uiState.value.password != _uiState.value.confirmPassword) {
                _uiState.update { it.copy(error = Failure.PasswordsDoNotMatch) }
                return
            }

            _uiState.update { it.copy(isLoading = true) }
            registerUserUseCase
                .run(RegisterUserUseCase.Params(_uiState.value.email, _uiState.value.password))
                .onEach { result ->
                    when (result) {
                        is Either.Left -> _uiState.update { it.copy(isLoading = false, error = result.a) }
                        is Either.Right -> {
                            loginUserUseCase
                                .run(LoginUserUseCase.Params(_uiState.value.email, _uiState.value.password))
                                .onEach { loginResult ->
                                    when (loginResult) {
                                        is Either.Left -> _uiState.update { it.copy(isLoading = false, error = loginResult.a) }
                                        is Either.Right -> {
                                            _uiState.update { it.copy(isLoading = false, isSuccess = true) }
                                            navigate(NavigationEvent.PopAndTo(Screens.Dashboard, Screens.Register.route))
                                        }
                                    }
                                }.launchIn(viewModelScope)
                        }
                    }
                }.launchIn(viewModelScope)
        }
    }

data class RegisterUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: com.vitocuaderno.gweather.core.exceptions.Failure? = null,
)
