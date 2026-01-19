package com.vitocuaderno.gweather.presentation.login

import com.google.common.truth.Truth.assertThat
import com.vitocuaderno.gweather.core.exceptions.Either
import com.vitocuaderno.gweather.core.exceptions.Failure
import com.vitocuaderno.gweather.domain.usecase.user.LoginUserUseCase
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
class LoginViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var loginUserUseCase: LoginUserUseCase
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        loginUserUseCase = mockk()
        viewModel = LoginViewModel(loginUserUseCase)
    }

    @Test
    fun `Initial state is correct`() =
        runTest {
            val initialState = LoginUiState()
            assertThat(viewModel.uiState.value).isEqualTo(initialState)
        }

    @Test
    fun `Login successful, navigates to dashboard`() =
        runTest {
            coEvery { loginUserUseCase.run(any()) } returns flowOf(Either.Right(mockk()))

            viewModel.login()

            assertThat(viewModel.navigation).isNotNull()
        }

    @Test
    fun `Login fails, shows error`() =
        runTest {
            coEvery { loginUserUseCase.run(any()) } returns flowOf(Either.Left(Failure.ServerError))

            viewModel.login()

            val uiState = viewModel.uiState.value
            assertThat(uiState.error).isNotNull()
        }
}
