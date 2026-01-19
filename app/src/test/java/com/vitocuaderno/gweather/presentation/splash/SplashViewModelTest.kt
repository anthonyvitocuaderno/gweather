package com.vitocuaderno.gweather.presentation.splash

import com.google.common.truth.Truth.assertThat
import com.vitocuaderno.gweather.core.exceptions.Either
import com.vitocuaderno.gweather.domain.usecase.user.GetUserUseCase
import com.vitocuaderno.gweather.rules.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SplashViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var getUserUseCase: GetUserUseCase
    private lateinit var viewModel: SplashViewModel

    @Test
    fun `User logged in, navigates to dashboard`() =
        runTest {
            getUserUseCase = mockk()
            coEvery { getUserUseCase.run(Unit) } returns flowOf(Either.Right(mockk()))
            viewModel = SplashViewModel(getUserUseCase)

            assertThat(viewModel.navigation).isNotNull()
        }

    @Test
    fun `User not logged in, navigates to register`() =
        runTest {
            getUserUseCase = mockk()
            coEvery { getUserUseCase.run(Unit) } returns flowOf(Either.Left(mockk()))
            viewModel = SplashViewModel(getUserUseCase)

            assertThat(viewModel.navigation).isNotNull()
        }
}
