package com.vitocuaderno.gweather.presentation.dashboard

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.vitocuaderno.gweather.presentation.dashboard.currentweather.CurrentWeatherScreen
import com.vitocuaderno.gweather.presentation.dashboard.history.HistoryScreen
import com.vitocuaderno.gweather.presentation.navigation.NavigationEvent
import com.vitocuaderno.gweather.presentation.navigation.Navigator
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navigator: Navigator,
    viewModel: DashboardViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    val locationPermissionState =
        rememberPermissionState(
            Manifest.permission.ACCESS_FINE_LOCATION,
            onPermissionResult = {
                viewModel.onPermissionResult(it)
            },
        )

    val locationSettingsLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult(),
            onResult = { viewModel.onLocationEnabledResult(true) },
        )

    LaunchedEffect(key1 = viewModel.navigation) {
        viewModel.navigation.collectLatest { nav ->
            when (nav) {
                is NavigationEvent.To -> navigator.navigate(nav.screen)
                is NavigationEvent.PopAndTo -> navigator.navigateAndClearBackStack(nav.screen, nav.popUpTo)
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.checkLocationSettings()
    }

    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Current Weather", "History")

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text(uiState.email) },
                    actions = {
                        Button(onClick = { viewModel.logout() }) {
                            Text("Logout")
                        }
                    },
                )
                if (uiState.isLocationPermissionGranted && uiState.isLocationEnabled) {
                    TabRow(selectedTabIndex = selectedTab) {
                        tabs.forEachIndexed { index, title ->
                            Tab(
                                selected = selectedTab == index,
                                onClick = { selectedTab = index },
                                text = { Text(title) },
                            )
                        }
                    }
                }
            }
        },
    ) { paddingValues ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            if (!uiState.isLocationPermissionGranted) {
                Button(onClick = { locationPermissionState.launchPermissionRequest() }) {
                    Text("Request Location Permission")
                }
            } else if (!uiState.isLocationEnabled) {
                Button(onClick = { viewModel.requestLocationEnable() }) {
                    Text("Enable Location Service")
                }
            } else {
                when (selectedTab) {
                    0 -> CurrentWeatherScreen()
                    1 -> HistoryScreen()
                }
            }
        }
    }
}
