package com.vitocuaderno.gweather.domain.repository

import androidx.paging.PagingData
import com.vitocuaderno.gweather.core.base.BaseRepository
import com.vitocuaderno.gweather.core.exceptions.Either
import com.vitocuaderno.gweather.core.exceptions.Failure
import com.vitocuaderno.gweather.domain.model.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository : BaseRepository {
    suspend fun saveWeather(weather: Weather): Either<Failure, Unit>

    fun getLatestWeather(userEmail: String): Flow<Either<Failure, Weather?>>

    fun getAllWeathers(userEmail: String): Flow<PagingData<Weather>>

    suspend fun fetchCurrentWeather(
        lat: Double,
        lon: Double,
    ): Either<Failure, Weather>
}
