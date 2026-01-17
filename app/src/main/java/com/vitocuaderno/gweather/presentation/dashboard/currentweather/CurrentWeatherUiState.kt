package com.vitocuaderno.gweather.presentation.dashboard.currentweather

import com.vitocuaderno.gweather.domain.model.Weather

data class CurrentWeatherUiState(
    val isLoading: Boolean = false,
    val weather: Weather? = null,
    val error: com.vitocuaderno.gweather.core.exceptions.Failure? = null,
)
