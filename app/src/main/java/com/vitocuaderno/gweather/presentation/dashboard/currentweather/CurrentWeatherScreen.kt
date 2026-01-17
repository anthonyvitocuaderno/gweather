package com.vitocuaderno.gweather.presentation.dashboard.currentweather

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vitocuaderno.gweather.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun CurrentWeatherScreen(viewModel: CurrentWeatherViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchWeather()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator()
        } else if (uiState.error != null) {
            Text(text = "Failed to fetch weather: ${uiState.error}")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { viewModel.fetchWeather() }) {
                Text("Refresh")
            }
        } else if (uiState.weather != null) {
            val weather = uiState.weather!!
            val iconRes =
                when (weather.weatherIcon) {
                    "sun" -> R.drawable.ic_sun
                    "moon" -> R.drawable.ic_moon
                    "rain" -> R.drawable.ic_rain
                    else -> null
                }

            if (iconRes != null) {
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = weather.weatherIcon,
                    modifier = Modifier.size(128.dp),
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            Text(text = "Current Weather in ${weather.city}, ${weather.country}")
            Text(text = "${weather.celsius}°C")
            Text(text = "Sunrise: ${weather.sunrise.toFormattedTime()}")
            Text(text = "Sunset: ${weather.sunset.toFormattedTime()}")
            Text(text = "As of: ${weather.createdAt.toFormattedDateTime()}")
        } else {
            Text(text = "No weather data available. Try again later.")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { viewModel.fetchWeather() }) {
                Text("Refresh")
            }
        }
    }
}

private fun Long.toFormattedTime(): String {
    if (this == 0L) return "n/a"
    val date = Date(this * 1000)
    val format = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return format.format(date)
}

private fun Long.toFormattedDateTime(): String {
    if (this == 0L) return "n/a"
    val date = Date(this * 1000)
    val format = SimpleDateFormat("MMMM dd, yyyy hh:mm a", Locale.getDefault())
    return format.format(date)
}
