package com.vitocuaderno.gweather.presentation.dashboard.history

import androidx.paging.PagingData
import com.google.common.truth.Truth.assertThat
import com.vitocuaderno.gweather.domain.usecase.weather.GetAllWeathers
import com.vitocuaderno.gweather.rules.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HistoryViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var getAllWeathers: GetAllWeathers
    private lateinit var viewModel: HistoryViewModel

    @Before
    fun setUp() {
        getAllWeathers = mockk()
        coEvery { getAllWeathers.run() } returns flowOf(PagingData.empty())
        viewModel = HistoryViewModel(getAllWeathers)
    }

    @Test
    fun `Weathers are fetched on init`() =
        runTest {
            val result = viewModel.weathers.first()
            assertThat(result).isNotNull()
        }
}
