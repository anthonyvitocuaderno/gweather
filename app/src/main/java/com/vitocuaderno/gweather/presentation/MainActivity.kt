@file:OptIn(ExperimentalMaterial3Api::class)

package com.vitocuaderno.gweather.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vitocuaderno.gweather.core.theme.GWeatherTheme
import com.vitocuaderno.gweather.presentation.dashboard.DashboardScreen
import com.vitocuaderno.gweather.presentation.login.LoginScreen
import com.vitocuaderno.gweather.presentation.navigation.NavigatorImpl
import com.vitocuaderno.gweather.presentation.navigation.Screens
import com.vitocuaderno.gweather.presentation.register.RegisterScreen
import com.vitocuaderno.gweather.presentation.splash.SplashScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GWeatherTheme {
                GWeatherApp()
            }
        }
    }
}

@Composable
fun GWeatherApp() {
    val navController = rememberNavController()
    val navigator = remember(navController) { NavigatorImpl(navController) }

    NavHost(navController = navController, startDestination = Screens.Splash.route) {
        composable(Screens.Splash.route) {
            SplashScreen(navigator = navigator)
        }
        composable(Screens.Register.route) {
            RegisterScreen(navigator = navigator)
        }
        composable(Screens.Login.route) {
            LoginScreen(navigator = navigator)
        }
        composable(Screens.Dashboard.route) {
            DashboardScreen(navigator = navigator)
        }
    }
}
