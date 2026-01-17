package com.vitocuaderno.gweather.data.datasource.local

import androidx.paging.PagingSource
import com.vitocuaderno.gweather.data.datasource.local.entity.WeatherEntityLocal
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherLocalDataSourceImpl
    @Inject
    constructor(
        private val weatherDao: WeatherDao,
    ) : WeatherLocalDataSource {
        override suspend fun saveWeather(weather: WeatherEntityLocal) {
            weatherDao.insertWeather(weather)
        }

        override fun getLatestWeather(userEmail: String): Flow<WeatherEntityLocal?> = weatherDao.getLatestWeather(userEmail)

        override fun getAllWeathers(userEmail: String): PagingSource<Int, WeatherEntityLocal> = weatherDao.getAllWeathers(userEmail)
    }
