package com.vitocuaderno.gweather.domain.repository

import androidx.paging.PagingData
import com.vitocuaderno.gweather.core.exceptions.Either
import com.vitocuaderno.gweather.core.exceptions.Failure
import com.vitocuaderno.gweather.domain.model.Weather
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeWeatherRepository : WeatherRepository {
    private val weathers = mutableListOf<Weather>()
    private var shouldReturnError = false

    fun setShouldReturnError(value: Boolean) {
        shouldReturnError = value
    }

    override suspend fun saveWeather(weather: Weather): Either<Failure, Unit> {
        if (shouldReturnError) {
            return Either.Left(Failure.ServerError)
        }
        weathers.removeIf { it.id == weather.id && it.id != 0 } // Simulate insert/replace
        weathers.add(weather)
        return Either.Right(Unit)
    }

    override fun getLatestWeather(userEmail: String): Flow<Either<Failure, Weather?>> {
        if (shouldReturnError) {
            return flowOf(Either.Left(Failure.ServerError))
        }
        val latest = weathers.filter { it.userEmail == userEmail }.maxByOrNull { it.createdAt }
        return flowOf(Either.Right(latest))
    }

    override fun getAllWeathers(userEmail: String): Flow<PagingData<Weather>> {
        val userWeathers = weathers.filter { it.userEmail == userEmail }
        return flowOf(PagingData.from(userWeathers))
    }

    override suspend fun fetchCurrentWeather(
        lat: Double,
        lon: Double,
    ): Either<Failure, Weather> {
        if (shouldReturnError) {
            return Either.Left(Failure.NetworkConnection)
        }
        return Either.Right(
            Weather(
                id = 1,
                userEmail = "", // This will be updated by the use case
                city = "Fake City",
                country = "FC",
                lat = lat,
                lon = lon,
                celsius = 25.0,
                sunrise = 1661834187,
                sunset = 1661882187,
                weatherIcon = "sun",
                createdAt = System.currentTimeMillis() / 1000,
            ),
        )
    }
}
