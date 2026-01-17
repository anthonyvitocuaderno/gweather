package com.vitocuaderno.gweather.data.datasource.remote

import com.vitocuaderno.gweather.core.exceptions.Either
import com.vitocuaderno.gweather.core.exceptions.Failure
import com.vitocuaderno.gweather.data.datasource.remote.entity.WeatherEntityRemote
import javax.inject.Inject

class WeatherRemoteDataSourceImpl
    @Inject
    constructor(
        private val apiClient: ApiClient,
    ) : WeatherRemoteDataSource {
        override suspend fun getCurrentWeather(
            lat: Double,
            lon: Double,
        ): Either<Failure, WeatherEntityRemote> =
            try {
                val response = apiClient.getCurrentWeather(lat, lon)
                Either.Right(response)
            } catch (e: Exception) {
                Either.Left(Failure.ServerError)
            }
    }
