package com.vitocuaderno.gweather.data.mapper

import com.vitocuaderno.gweather.data.datasource.local.entity.WeatherEntityLocal
import com.vitocuaderno.gweather.data.datasource.remote.entity.WeatherEntityRemote
import com.vitocuaderno.gweather.domain.model.Weather
import java.util.Calendar

fun WeatherEntityLocal.toDomain(): Weather =
    Weather(
        id = id,
        userEmail = userEmail,
        city = city,
        country = country,
        lat = lat,
        lon = lon,
        celsius = celsius,
        sunrise = sunrise,
        sunset = sunset,
        weatherIcon = weatherIcon,
        createdAt = createdAt,
    )

fun Weather.toEntity(): WeatherEntityLocal =
    WeatherEntityLocal(
        id = id,
        userEmail = userEmail,
        city = city,
        country = country,
        lat = lat,
        lon = lon,
        celsius = celsius,
        sunrise = sunrise,
        sunset = sunset,
        weatherIcon = weatherIcon,
        createdAt = createdAt,
    )

fun WeatherEntityRemote.toDomain(): Weather {
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)

    val weatherIcon =
        if (weather[0].main == "Rain") {
            "rain"
        } else if (hour < 18) {
            "sun"
        } else {
            "moon"
        }

    return Weather(
        id = 0, // Not available from remote
        userEmail = "", // Will be set in the repository
        city = name,
        country = sys.country,
        lat = coord.lat,
        lon = coord.lon,
        celsius = main.temp,
        sunrise = sys.sunrise,
        sunset = sys.sunset,
        weatherIcon = weatherIcon,
        createdAt = dt,
    )
}
