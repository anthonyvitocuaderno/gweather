package com.vitocuaderno.gweather.presentation.register

import com.google.common.truth.Truth.assertThat
import com.vitocuaderno.gweather.core.exceptions.Either
import com.vitocuaderno.gweather.core.exceptions.Failure
import com.vitocuaderno.gweather.domain.usecase.user.LoginUserUseCase
import com.vitocuaderno.gweather.domain.usecase.user.RegisterUserUseCase
import com.vitocuaderno.gweather.rules.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class RegisterViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var registerUserUseCase: RegisterUserUseCase
    private lateinit var loginUserUseCase: LoginUserUseCase
    private lateinit var viewModel: RegisterViewModel

    @Before
    fun setUp() {
        registerUserUseCase = mockk()
        loginUserUseCase = mockk()
        viewModel = RegisterViewModel(registerUserUseCase, loginUserUseCase)
    }

    @Test
    fun `Initial state is correct`() =
        runTest {
            val initialState = RegisterUiState()
            assertThat(viewModel.uiState.value).isEqualTo(initialState)
        }

    @Test
    fun `Register successful, navigates to dashboard`() =
        runTest {
            coEvery { registerUserUseCase.run(any()) } returns flowOf(Either.Right(Unit))
            coEvery { loginUserUseCase.run(any()) } returns flowOf(Either.Right(mockk()))

            viewModel.register()

            assertThat(viewModel.navigation).isNotNull()
        }

    @Test
    fun `Register fails, shows error`() =
        runTest {
            coEvery { registerUserUseCase.run(any()) } returns flowOf(Either.Left(Failure.ServerError))

            viewModel.register()

            val uiState = viewModel.uiState.value
            assertThat(uiState.error).isNotNull()
        }
}
