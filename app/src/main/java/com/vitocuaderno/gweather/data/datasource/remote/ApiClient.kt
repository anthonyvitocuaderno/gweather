package com.vitocuaderno.gweather.data.datasource.remote

import com.vitocuaderno.gweather.BuildConfig
import com.vitocuaderno.gweather.data.datasource.remote.entity.UserEntityRemote
import com.vitocuaderno.gweather.data.datasource.remote.entity.WeatherEntityRemote
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiClient {
    @POST("auth/register")
    suspend fun register(
        @Body request: UserEntityRemote,
    ): UserEntityRemote

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String = "metric",
        @Query("appid") apiKey: String = BuildConfig.API_KEY,
    ): WeatherEntityRemote
}
