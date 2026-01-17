package com.vitocuaderno.gweather.presentation.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.vitocuaderno.gweather.presentation.navigation.NavigationEvent
import com.vitocuaderno.gweather.presentation.navigation.Navigator
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navigator: Navigator,
    viewModel: DashboardViewModel = hiltViewModel(),
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Tab 1", "Tab 2")

    LaunchedEffect(key1 = viewModel.navigation) {
        viewModel.navigation.collectLatest { nav ->
            when (nav) {
                is NavigationEvent.To -> navigator.navigate(nav.screen)
                is NavigationEvent.PopAndTo -> navigator.navigateAndClearBackStack(nav.screen, nav.popUpTo)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dashboard") },
                actions = {
                    Button(onClick = { viewModel.logout() }) {
                        Text("Logout")
                    }
                },
            )
        },
    ) {
        Column {
            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) },
                    )
                }
            }
            when (selectedTab) {
                0 -> TabContent(title = "Tab 1 Content")
                1 -> TabContent(title = "Tab 2 Content")
            }
        }
    }
}

@Composable
fun TabContent(title: String) {
    Text(text = title)
}
