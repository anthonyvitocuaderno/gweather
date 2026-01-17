package com.vitocuaderno.gweather.presentation.navigation

sealed class NavigationEvent {
    data class To(
        val screen: Screens,
    ) : NavigationEvent()

    data class PopAndTo(
        val screen: Screens,
        val popUpTo: String,
    ) : NavigationEvent()
}
