package com.vitocuaderno.gweather.presentation.dashboard

import androidx.lifecycle.viewModelScope
import com.vitocuaderno.gweather.core.exceptions.Either
import com.vitocuaderno.gweather.domain.usecase.location.CheckLocationPermission
import com.vitocuaderno.gweather.domain.usecase.location.GetCurrentLocation
import com.vitocuaderno.gweather.domain.usecase.location.IsLocationEnabled
import com.vitocuaderno.gweather.domain.usecase.location.RequestLocationEnable
import com.vitocuaderno.gweather.domain.usecase.user.GetUserUseCase
import com.vitocuaderno.gweather.domain.usecase.user.LogoutUserUseCase
import com.vitocuaderno.gweather.presentation.base.BaseViewModel
import com.vitocuaderno.gweather.presentation.navigation.NavigationEvent
import com.vitocuaderno.gweather.presentation.navigation.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel
    @Inject
    constructor(
        private val logoutUserUseCase: LogoutUserUseCase,
        private val isLocationEnabled: IsLocationEnabled,
        private val requestLocationEnable: RequestLocationEnable,
        private val checkLocationPermission: CheckLocationPermission,
        private val getCurrentLocation: GetCurrentLocation,
        private val getUserUseCase: GetUserUseCase,
    ) : BaseViewModel() {
        private val _uiState = MutableStateFlow(DashboardUiState())
        val uiState: StateFlow<DashboardUiState> = _uiState

        init {
            getUser()
        }

        private fun getUser() {
            getUserUseCase
                .run(Unit)
                .onEach { result ->
                    when (result) {
                        is Either.Left -> {
                            // Handle error
                        }
                        is Either.Right -> {
                            _uiState.update { it.copy(email = result.b.email.value) }
                        }
                    }
                }.launchIn(viewModelScope)
        }

        fun onPermissionResult(isGranted: Boolean) {
            _uiState.update { it.copy(isLocationPermissionGranted = isGranted) }
            if (isGranted) {
                getCurrentLocation()
            }
        }

        fun onLocationEnabledResult(isEnabled: Boolean) {
            _uiState.update { it.copy(isLocationEnabled = isEnabled) }
            if (isEnabled) {
                getCurrentLocation()
            }
        }

        fun checkLocationSettings() {
            _uiState.update { it.copy(isLocationPermissionGranted = checkLocationPermission()) }
            _uiState.update { it.copy(isLocationEnabled = isLocationEnabled()) }

            if (checkLocationPermission() && isLocationEnabled()) {
                getCurrentLocation()
            }
        }

        private fun getCurrentLocation() {
            getCurrentLocation
                .invoke()
                .onEach { result ->
                    // TODO: Handle location result
                }.launchIn(viewModelScope)
        }

        fun requestLocationEnable() {
            requestLocationEnable.invoke()
        }

        fun logout() {
            logoutUserUseCase
                .run(Unit)
                .onEach {
                    navigate(NavigationEvent.PopAndTo(Screens.Login, Screens.Dashboard.route))
                }.launchIn(viewModelScope)
        }
    }

data class DashboardUiState(
    val isLocationPermissionGranted: Boolean = false,
    val isLocationEnabled: Boolean = false,
    val email: String = "",
)
