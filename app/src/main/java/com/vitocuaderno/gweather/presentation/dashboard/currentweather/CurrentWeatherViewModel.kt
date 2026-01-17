package com.vitocuaderno.gweather.presentation.dashboard.currentweather

import androidx.lifecycle.viewModelScope
import com.vitocuaderno.gweather.core.exceptions.Either
import com.vitocuaderno.gweather.domain.usecase.location.GetCurrentLocation
import com.vitocuaderno.gweather.domain.usecase.weather.FetchAndSaveWeatherUseCase
import com.vitocuaderno.gweather.domain.usecase.weather.GetCurrentWeather
import com.vitocuaderno.gweather.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel
    @Inject
    constructor(
        private val getCurrentLocation: GetCurrentLocation,
        private val fetchAndSaveWeather: FetchAndSaveWeatherUseCase,
        private val getCurrentWeather: GetCurrentWeather,
    ) : BaseViewModel() {
        private val _uiState = MutableStateFlow(CurrentWeatherUiState())
        val uiState: StateFlow<CurrentWeatherUiState> = _uiState

        init {
            observeLatestWeather()
        }

        private fun observeLatestWeather() {
            getCurrentWeather
                .run()
                .onEach { result ->
                    when (result) {
                        is Either.Left -> _uiState.update { it.copy(isLoading = false, error = result.a) }
                        is Either.Right -> _uiState.update { it.copy(isLoading = false, weather = result.b) }
                    }
                }.launchIn(viewModelScope)
        }

        fun fetchWeather() {
            _uiState.update { it.copy(isLoading = true, error = null) }
            getCurrentLocation()
                .onEach { locationResult ->
                    when (locationResult) {
                        is Either.Left -> {
                            _uiState.update { it.copy(isLoading = false, error = locationResult.a) }
                        }
                        is Either.Right -> {
                            val params =
                                FetchAndSaveWeatherUseCase.Params(
                                    lat = locationResult.b.latitude,
                                    lon = locationResult.b.longitude,
                                )
                            when (val result = fetchAndSaveWeather.run(params)) {
                                is Either.Left -> {
                                    _uiState.update { it.copy(isLoading = false, error = result.a) }
                                }
                                is Either.Right -> {
                                    // Success is handled by the observer in init }
                                }
                            }
                        }
                    }
                }.launchIn(viewModelScope)
        }
    }
