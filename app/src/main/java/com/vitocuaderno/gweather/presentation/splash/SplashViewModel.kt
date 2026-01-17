package com.vitocuaderno.gweather.presentation.splash

import androidx.lifecycle.viewModelScope
import com.vitocuaderno.gweather.core.exceptions.Either
import com.vitocuaderno.gweather.domain.usecase.user.GetUserUseCase
import com.vitocuaderno.gweather.presentation.base.BaseViewModel
import com.vitocuaderno.gweather.presentation.navigation.NavigationEvent
import com.vitocuaderno.gweather.presentation.navigation.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SplashViewModel
    @Inject
    constructor(
        private val getUserUseCase: GetUserUseCase,
    ) : BaseViewModel() {
        init {
            checkUserSession()
        }

        private fun checkUserSession() {
            getUserUseCase
                .run(Unit)
                .onEach { result ->
                    val destination =
                        when (result) {
                            is Either.Left -> Screens.Register
                            is Either.Right -> Screens.Dashboard
                        }
                    navigate(NavigationEvent.PopAndTo(destination, Screens.Splash.route))
                }.launchIn(viewModelScope)
        }
    }
