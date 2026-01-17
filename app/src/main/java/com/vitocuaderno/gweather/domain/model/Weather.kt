package com.vitocuaderno.gweather.domain.model

data class Weather(
    val id: Int,
    val userEmail: String,
    val city: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val celsius: Double,
    val sunrise: Long,
    val sunset: Long,
    val weatherIcon: String,
    val createdAt: Long,
)
