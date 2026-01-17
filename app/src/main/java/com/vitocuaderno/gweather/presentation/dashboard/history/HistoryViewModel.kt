package com.vitocuaderno.gweather.presentation.dashboard.history

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.vitocuaderno.gweather.domain.model.Weather
import com.vitocuaderno.gweather.domain.usecase.weather.GetAllWeathers
import com.vitocuaderno.gweather.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getAllWeathers: GetAllWeathers
) : BaseViewModel() {

    val weathers: Flow<PagingData<Weather>> = getAllWeathers.run().cachedIn(viewModelScope)
}
