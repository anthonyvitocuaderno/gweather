package com.vitocuaderno.gweather.domain.usecase.weather

import com.google.common.truth.Truth.assertThat
import com.vitocuaderno.gweather.domain.FakeAuthRepository
import com.vitocuaderno.gweather.domain.TestStubs
import com.vitocuaderno.gweather.domain.repository.FakeGeocoderRepository
import com.vitocuaderno.gweather.domain.repository.FakeWeatherRepository
import com.vitocuaderno.gweather.domain.usecase.user.GetUserUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class FetchAndSaveWeatherUseCaseTest {
    private lateinit var fetchAndSaveWeatherUseCase: FetchAndSaveWeatherUseCase
    private lateinit var geocoderRepository: FakeGeocoderRepository
    private lateinit var weatherRepository: FakeWeatherRepository
    private lateinit var getUserUseCase: GetUserUseCase

    @Before
    fun setUp() {
        geocoderRepository = FakeGeocoderRepository()
        weatherRepository = FakeWeatherRepository()
        val authRepository = FakeAuthRepository()
        getUserUseCase = GetUserUseCase(authRepository)
        fetchAndSaveWeatherUseCase =
            FetchAndSaveWeatherUseCase(
                geocoderRepository,
                weatherRepository,
                getUserUseCase,
            )

        // Pre-populate with a logged in user
        runTest {
            authRepository.register(TestStubs.testUser, TestStubs.testPassword)
            authRepository.login(TestStubs.testEmail, TestStubs.testPassword).first()
        }
    }

    @Test
    fun `Fetch and save weather, returns success`() =
        runTest {
            val result = fetchAndSaveWeatherUseCase.run(FetchAndSaveWeatherUseCase.Params(0.0, 0.0))
            assertThat(result.isRight).isTrue()
        }

    @Test
    fun `Geocoder fails, returns failure`() =
        runTest {
            geocoderRepository.setShouldReturnError(true)
            val result = fetchAndSaveWeatherUseCase.run(FetchAndSaveWeatherUseCase.Params(0.0, 0.0))
            assertThat(result.isLeft).isTrue()
        }

    @Test
    fun `Weather fetch fails, returns failure`() =
        runTest {
            weatherRepository.setShouldReturnError(true)
            val result = fetchAndSaveWeatherUseCase.run(FetchAndSaveWeatherUseCase.Params(0.0, 0.0))
            assertThat(result.isLeft).isTrue()
        }
}
