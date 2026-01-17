package com.vitocuaderno.gweather.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vitocuaderno.gweather.presentation.navigation.NavigationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {
    private val _navigation = Channel<NavigationEvent>()
    val navigation = _navigation.receiveAsFlow()

    fun navigate(event: NavigationEvent) {
        viewModelScope.launch {
            _navigation.send(event)
        }
    }
}
