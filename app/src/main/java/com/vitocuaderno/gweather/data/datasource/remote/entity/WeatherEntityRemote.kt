package com.vitocuaderno.gweather.data.datasource.remote.entity

import com.google.gson.annotations.SerializedName

data class WeatherEntityRemote(
    @SerializedName("coord")
    val coord: Coord,
    @SerializedName("weather")
    val weather: List<WeatherInfo>,
    @SerializedName("main")
    val main: Main,
    @SerializedName("dt")
    val dt: Long,
    @SerializedName("sys")
    val sys: Sys,
    @SerializedName("name")
    val name: String,
) {
    data class Coord(
        @SerializedName("lon")
        val lon: Double,
        @SerializedName("lat")
        val lat: Double,
    )

    data class WeatherInfo(
        @SerializedName("main")
        val main: String,
    )

    data class Main(
        @SerializedName("temp")
        val temp: Double,
    )

    data class Sys(
        @SerializedName("country")
        val country: String,
        @SerializedName("sunrise")
        val sunrise: Long,
        @SerializedName("sunset")
        val sunset: Long,
    )
}
