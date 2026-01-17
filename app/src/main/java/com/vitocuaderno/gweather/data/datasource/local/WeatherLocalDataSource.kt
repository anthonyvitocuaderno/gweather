package com.vitocuaderno.gweather.data.datasource.local

import androidx.paging.PagingSource
import com.vitocuaderno.gweather.data.datasource.local.entity.WeatherEntityLocal
import kotlinx.coroutines.flow.Flow

interface WeatherLocalDataSource {
    suspend fun saveWeather(weather: WeatherEntityLocal)

    fun getLatestWeather(userEmail: String): Flow<WeatherEntityLocal?>

    fun getAllWeathers(userEmail: String): PagingSource<Int, WeatherEntityLocal>
}
