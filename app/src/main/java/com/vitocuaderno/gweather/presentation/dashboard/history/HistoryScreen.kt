package com.vitocuaderno.gweather.presentation.dashboard.history

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.vitocuaderno.gweather.R
import com.vitocuaderno.gweather.domain.model.Weather
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val weathers = viewModel.weathers.collectAsLazyPagingItems()

    Column(modifier = Modifier.fillMaxSize()) {
        if (weathers.loadState.refresh is LoadState.Loading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (weathers.itemCount == 0) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "No weather history yet.")
            }
        } else {
            LazyColumn {
                var lastHeader = ""
                items(weathers.itemCount) { index ->
                    val weather = weathers[index]
                    if (weather != null) {
                        val header = weather.createdAt.toDateHeader()
                        if (header != lastHeader) {
                            Text(text = header, modifier = Modifier.padding(8.dp))
                            lastHeader = header
                        }
                        WeatherRow(weather = weather)
                    }
                }
            }
        }
    }
}

@Composable
fun WeatherRow(weather: Weather) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "${weather.celsius}°C", fontSize = 24.sp)
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = "${weather.city}, ${weather.country}")
            Text(text = weather.createdAt.toTimeAgo(), fontSize = 12.sp)
        }
        Spacer(modifier = Modifier.weight(1f))
        val iconRes = when (weather.weatherIcon) {
            "sun" -> R.drawable.ic_sun
            "moon" -> R.drawable.ic_moon
            "rain" -> R.drawable.ic_rain
            else -> null
        }

        if (iconRes != null) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = weather.weatherIcon,
                modifier = Modifier.size(48.dp)
            )
        }
    }
}

private fun Long.toTimeAgo(): String {
    val now = System.currentTimeMillis()
    val diff = now - (this * 1000)

    val minutes = TimeUnit.MILLISECONDS.toMinutes(diff)
    val hours = TimeUnit.MILLISECONDS.toHours(diff)

    return when {
        minutes < 1 -> "just now"
        minutes < 60 -> "$minutes minutes ago"
        else -> java.text.SimpleDateFormat("hh:mm a", Locale.getDefault()).format(java.util.Date(this * 1000))
    }
}

private fun Long.toDateHeader(): String {
    val calendar = Calendar.getInstance()
    val today = calendar.get(Calendar.DAY_OF_YEAR)
    calendar.timeInMillis = this * 1000
    val dateDay = calendar.get(Calendar.DAY_OF_YEAR)

    return if (today == dateDay) {
        "Today"
    } else {
        SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(Date(this * 1000))
    }
}
