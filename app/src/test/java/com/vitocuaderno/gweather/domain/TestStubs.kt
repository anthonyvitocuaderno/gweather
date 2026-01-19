package com.vitocuaderno.gweather.domain

import com.vitocuaderno.gweather.core.exceptions.Either
import com.vitocuaderno.gweather.domain.model.User
import com.vitocuaderno.gweather.domain.model.Weather
import com.vitocuaderno.gweather.domain.valueobjects.Email
import com.vitocuaderno.gweather.domain.valueobjects.Password

object TestStubs {
    val testEmail = (Email.create("test@email.com") as Either.Right).b
    val testPassword = (Password.create("Password123") as Either.Right).b
    val testUser = User(testEmail)
    val testWeather =
        Weather(
            id = 1,
            userEmail = testEmail.value,
            city = "Test City",
            country = "TC",
            lat = 0.0,
            lon = 0.0,
            celsius = 25.0,
            sunrise = 1661834187,
            sunset = 1661882187,
            weatherIcon = "sun",
            createdAt = System.currentTimeMillis() / 1000,
        )
}
