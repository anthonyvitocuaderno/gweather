package com.vitocuaderno.gweather.presentation.dashboard.currentweather

import com.google.common.truth.Truth.assertThat
import com.vitocuaderno.gweather.core.exceptions.Either
import com.vitocuaderno.gweather.domain.TestStubs
import com.vitocuaderno.gweather.domain.model.Location
import com.vitocuaderno.gweather.domain.usecase.location.GetCurrentLocation
import com.vitocuaderno.gweather.domain.usecase.weather.FetchAndSaveWeatherUseCase
import com.vitocuaderno.gweather.domain.usecase.weather.GetCurrentWeather
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
class CurrentWeatherViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var getCurrentLocation: GetCurrentLocation
    private lateinit var fetchAndSaveWeather: FetchAndSaveWeatherUseCase
    private lateinit var getCurrentWeather: GetCurrentWeather
    private lateinit var viewModel: CurrentWeatherViewModel

    @Before
    fun setUp() {
        getCurrentLocation = mockk()
        fetchAndSaveWeather = mockk()
        getCurrentWeather = mockk()

        coEvery { getCurrentWeather.run() } returns flowOf(Either.Right(TestStubs.testWeather))

        viewModel =
            CurrentWeatherViewModel(
                getCurrentLocation,
                fetchAndSaveWeather,
                getCurrentWeather,
            )
    }

    @Test
    fun `Initial state is correct`() =
        runTest {
            val initialState = CurrentWeatherUiState(weather = TestStubs.testWeather)
            assertThat(viewModel.uiState.value).isEqualTo(initialState)
        }

    @Test
    fun `Fetch weather successful, saves weather`() =
        runTest {
            val location = mockk<Location>()
            coEvery { location.latitude } returns 0.0
            coEvery { location.longitude } returns 0.0
            coEvery { getCurrentLocation.invoke() } returns flowOf(Either.Right(location))
            coEvery { fetchAndSaveWeather.run(any()) } returns Either.Right(Unit)

            viewModel.fetchWeather()

            assertThat(viewModel.uiState.value.weather).isNotNull()
        }
}
