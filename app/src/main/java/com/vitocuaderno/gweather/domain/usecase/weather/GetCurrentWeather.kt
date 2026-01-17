package com.vitocuaderno.gweather.domain.usecase.weather

import com.vitocuaderno.gweather.core.exceptions.Either
import com.vitocuaderno.gweather.core.exceptions.Failure
import com.vitocuaderno.gweather.domain.model.Weather
import com.vitocuaderno.gweather.domain.repository.WeatherRepository
import com.vitocuaderno.gweather.domain.usecase.user.GetUserUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCurrentWeather
    @Inject
    constructor(
        private val weatherRepository: WeatherRepository,
        private val getUserUseCase: GetUserUseCase,
    ) {
        fun run(): Flow<Either<Failure, Weather?>> =
            flow {
                val userResult = getUserUseCase.run(Unit).first()
                if (userResult is Either.Left) {
                    emit(Either.Left(userResult.a))
                    return@flow
                }
                val user = (userResult as Either.Right).b

                weatherRepository.getLatestWeather(user.email.value).collect {
                    emit(it)
                }
            }
    }
