package com.vitocuaderno.gweather.presentation.navigation

sealed class Screens(
    val route: String,
) {
    object Splash : Screens("splash")

    object Register : Screens("register")

    object Login : Screens("login")

    object Dashboard : Screens("dashboard")
}
