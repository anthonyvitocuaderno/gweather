package com.vitocuaderno.gweather.data.datasource.remote

import com.vitocuaderno.gweather.core.exceptions.Either
import com.vitocuaderno.gweather.core.exceptions.Failure
import com.vitocuaderno.gweather.data.datasource.remote.entity.WeatherEntityRemote

interface WeatherRemoteDataSource {
    suspend fun getCurrentWeather(
        lat: Double,
        lon: Double,
    ): Either<Failure, WeatherEntityRemote>
}
