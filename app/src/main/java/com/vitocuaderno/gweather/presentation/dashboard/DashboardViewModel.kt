package com.vitocuaderno.gweather.presentation.dashboard

import androidx.lifecycle.viewModelScope
import com.vitocuaderno.gweather.domain.usecase.user.LogoutUserUseCase
import com.vitocuaderno.gweather.presentation.base.BaseViewModel
import com.vitocuaderno.gweather.presentation.navigation.NavigationEvent
import com.vitocuaderno.gweather.presentation.navigation.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel
    @Inject
    constructor(
        private val logoutUserUseCase: LogoutUserUseCase,
    ) : BaseViewModel() {
        fun logout() {
            logoutUserUseCase
                .run(Unit)
                .launchIn(viewModelScope)
            navigate(NavigationEvent.PopAndTo(Screens.Register, Screens.Dashboard.route))
        }
    }
