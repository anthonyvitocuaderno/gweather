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

class GetAllWeathersTest {
    private lateinit var getAllWeathers: GetAllWeathers
    private lateinit var weatherRepository: FakeWeatherRepository
    private lateinit var authRepository: FakeAuthRepository
    private lateinit var getUserUseCase: GetUserUseCase

    @Before
    fun setUp() {
        weatherRepository = FakeWeatherRepository()
        authRepository = FakeAuthRepository()
        getUserUseCase = GetUserUseCase(authRepository)
        getAllWeathers = GetAllWeathers(weatherRepository, getUserUseCase)
    }

    @Test
    fun `User logged in, returns weather data`() =
        runTest {
            // Given a logged in user
            authRepository.register(TestStubs.testUser, TestStubs.testPassword)
            authRepository.login(TestStubs.testEmail, TestStubs.testPassword).first()
            weatherRepository.saveWeather(TestStubs.testWeather)

            // When
            val result = getAllWeathers.run().first()

            // Then
            assertThat(result).isNotNull()
        }
}
