package com.vitocuaderno.gweather.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.vitocuaderno.gweather.core.exceptions.Either
import com.vitocuaderno.gweather.core.exceptions.Failure
import com.vitocuaderno.gweather.data.datasource.local.WeatherLocalDataSource
import com.vitocuaderno.gweather.data.datasource.remote.WeatherRemoteDataSource
import com.vitocuaderno.gweather.data.mapper.toDomain
import com.vitocuaderno.gweather.data.mapper.toEntity
import com.vitocuaderno.gweather.domain.model.Weather
import com.vitocuaderno.gweather.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WeatherRepositoryImpl
    @Inject
    constructor(
        private val localDataSource: WeatherLocalDataSource,
        private val remoteDataSource: WeatherRemoteDataSource,
    ) : WeatherRepository {
        override suspend fun saveWeather(weather: Weather): Either<Failure, Unit> {
            localDataSource.saveWeather(weather.toEntity())
            return Either.Right(Unit)
        }

        override fun getLatestWeather(userEmail: String): Flow<Either<Failure, Weather?>> =
            localDataSource.getLatestWeather(userEmail).map { weatherEntity ->
                Either.Right(weatherEntity?.toDomain())
            }

        override fun getAllWeathers(userEmail: String): Flow<PagingData<Weather>> =
            Pager(
                config = PagingConfig(pageSize = 20),
                pagingSourceFactory = {
                    localDataSource.getAllWeathers(userEmail)
                },
            ).flow.map { pagingData ->
                pagingData.map { it.toDomain() }
            }

        override suspend fun fetchCurrentWeather(
            lat: Double,
            lon: Double,
        ): Either<Failure, Weather> =
            when (val result = remoteDataSource.getCurrentWeather(lat, lon)) {
                is Either.Left -> result
                is Either.Right -> Either.Right(result.b.toDomain())
            }
    }
