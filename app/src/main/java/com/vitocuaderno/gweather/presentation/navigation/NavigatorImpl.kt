package com.vitocuaderno.gweather.presentation.navigation

import androidx.navigation.NavController
import javax.inject.Inject

class NavigatorImpl
    @Inject
    constructor(
        override val navController: NavController,
    ) : Navigator {
        override fun navigate(screen: Screens) {
            navController.navigate(screen.route)
        }

        override fun navigateAndClearBackStack(
            screen: Screens,
            popUpTo: String,
        ) {
            navController.navigate(screen.route) {
                popUpTo(popUpTo) {
                    inclusive = true
                }
            }
        }
    }
