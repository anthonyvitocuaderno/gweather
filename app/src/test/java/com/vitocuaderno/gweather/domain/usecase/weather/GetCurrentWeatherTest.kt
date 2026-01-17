package com.vitocuaderno.gweather.domain.usecase.weather

import com.google.common.truth.Truth.assertThat
import com.vitocuaderno.gweather.domain.FakeAuthRepository
import com.vitocuaderno.gweather.domain.TestStubs
import com.vitocuaderno.gweather.domain.repository.FakeWeatherRepository
import com.vitocuaderno.gweather.domain.usecase.user.GetUserUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetCurrentWeatherTest {
    private lateinit var getCurrentWeather: GetCurrentWeather
    private lateinit var weatherRepository: FakeWeatherRepository
    private lateinit var getUserUseCase: GetUserUseCase

    @Before
    fun setUp() {
        weatherRepository = FakeWeatherRepository()
        val authRepository = FakeAuthRepository()
        getUserUseCase = GetUserUseCase(authRepository)
        getCurrentWeather = GetCurrentWeather(weatherRepository, getUserUseCase)

        // Pre-populate with a logged in user
        runTest {
            authRepository.register(TestStubs.testUser, TestStubs.testPassword)
            authRepository.login(TestStubs.testEmail, TestStubs.testPassword).first()
        }
    }

    @Test
    fun `Get current weather, returns weather`() =
        runTest {
            weatherRepository.saveWeather(TestStubs.testWeather)
            val result = getCurrentWeather.run().first()
            assertThat(result.isRight).isTrue()
        }

    @Test
    fun `Get current weather when no weather saved, returns null`() =
        runTest {
            val result = getCurrentWeather.run().first()
            assertThat((result as com.vitocuaderno.gweather.core.exceptions.Either.Right).b).isNull()
        }

    @Test
    fun `Repository returns error, returns failure`() =
        runTest {
            weatherRepository.setShouldReturnError(true)
            val result = getCurrentWeather.run().first()
            assertThat(result.isLeft).isTrue()
        }
}
