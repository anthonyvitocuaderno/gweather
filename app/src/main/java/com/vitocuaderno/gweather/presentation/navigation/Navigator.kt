package com.vitocuaderno.gweather.presentation.navigation

import androidx.navigation.NavController

interface Navigator {
    val navController: NavController

    fun navigate(screen: Screens)

    fun navigateAndClearBackStack(
        screen: Screens,
        popUpTo: String,
    )
}
