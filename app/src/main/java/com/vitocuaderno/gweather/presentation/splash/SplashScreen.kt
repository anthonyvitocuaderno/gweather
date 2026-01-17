package com.vitocuaderno.gweather.presentation.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.vitocuaderno.gweather.presentation.navigation.NavigationEvent
import com.vitocuaderno.gweather.presentation.navigation.Navigator
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SplashScreen(
    navigator: Navigator,
    viewModel: SplashViewModel = hiltViewModel(),
) {
    LaunchedEffect(key1 = viewModel.navigation) {
        viewModel.navigation.collectLatest { nav ->
            when (nav) {
                is NavigationEvent.To -> navigator.navigate(nav.screen)
                is NavigationEvent.PopAndTo -> navigator.navigateAndClearBackStack(nav.screen, nav.popUpTo)
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}
