package com.vitocuaderno.gweather.domain.usecase.weather

import androidx.paging.PagingData
import com.vitocuaderno.gweather.core.exceptions.Either
import com.vitocuaderno.gweather.domain.model.Weather
import com.vitocuaderno.gweather.domain.repository.WeatherRepository
import com.vitocuaderno.gweather.domain.usecase.user.GetUserUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class GetAllWeathers @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val getUserUseCase: GetUserUseCase
) {
    fun run(): Flow<PagingData<Weather>> {
        return getUserUseCase.run(Unit).flatMapLatest { userResult ->
            if (userResult is Either.Right) {
                weatherRepository.getAllWeathers(userResult.b.email.value)
            } else {
                flowOf(PagingData.empty())
            }
        }
    }
}
