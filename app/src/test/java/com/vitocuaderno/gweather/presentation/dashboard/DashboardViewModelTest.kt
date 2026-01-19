package com.vitocuaderno.gweather.presentation.dashboard

import com.google.common.truth.Truth.assertThat
import com.vitocuaderno.gweather.core.exceptions.Either
import com.vitocuaderno.gweather.domain.TestStubs
import com.vitocuaderno.gweather.domain.usecase.location.CheckLocationPermission
import com.vitocuaderno.gweather.domain.usecase.location.GetCurrentLocation
import com.vitocuaderno.gweather.domain.usecase.location.IsLocationEnabled
import com.vitocuaderno.gweather.domain.usecase.location.RequestLocationEnable
import com.vitocuaderno.gweather.domain.usecase.user.GetUserUseCase
import com.vitocuaderno.gweather.domain.usecase.user.LogoutUserUseCase
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
class DashboardViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var logoutUserUseCase: LogoutUserUseCase
    private lateinit var isLocationEnabled: IsLocationEnabled
    private lateinit var requestLocationEnable: RequestLocationEnable
    private lateinit var checkLocationPermission: CheckLocationPermission
    private lateinit var getCurrentLocation: GetCurrentLocation
    private lateinit var getUserUseCase: GetUserUseCase
    private lateinit var viewModel: DashboardViewModel

    @Before
    fun setUp() {
        logoutUserUseCase = mockk()
        isLocationEnabled = mockk()
        requestLocationEnable = mockk()
        checkLocationPermission = mockk()
        getCurrentLocation = mockk()
        getUserUseCase = mockk()

        coEvery { getUserUseCase.run(Unit) } returns flowOf(Either.Right(TestStubs.testUser))

        viewModel =
            DashboardViewModel(
                logoutUserUseCase,
                isLocationEnabled,
                requestLocationEnable,
                checkLocationPermission,
                getCurrentLocation,
                getUserUseCase,
            )
    }

    @Test
    fun `Initial state is correct`() =
        runTest {
            val initialState = DashboardUiState(email = TestStubs.testEmail.value)
            assertThat(viewModel.uiState.value).isEqualTo(initialState)
        }

    @Test
    fun `Logout successful, navigates to login`() =
        runTest {
            coEvery { logoutUserUseCase.run(Unit) } returns flowOf(Either.Right(Unit))

            viewModel.logout()

            assertThat(viewModel.navigation).isNotNull()
        }
}
