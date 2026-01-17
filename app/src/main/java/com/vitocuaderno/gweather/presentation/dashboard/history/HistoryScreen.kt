package com.vitocuaderno.gweather.presentation.dashboard.history

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HistoryScreen(viewModel: HistoryViewModel = hiltViewModel()) {
    // TODO: Implement UI to display weather history
    Text(text = "History")
}
