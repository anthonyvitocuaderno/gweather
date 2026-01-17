package com.vitocuaderno.gweather.domain.usecase.weather

import com.vitocuaderno.gweather.core.exceptions.Either
import com.vitocuaderno.gweather.core.exceptions.Failure
import com.vitocuaderno.gweather.domain.repository.GeocoderRepository
import com.vitocuaderno.gweather.domain.repository.WeatherRepository
import com.vitocuaderno.gweather.domain.usecase.user.GetUserUseCase
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class FetchAndSaveWeatherUseCase
    @Inject
    constructor(
        private val geocoderRepository: GeocoderRepository,
        private val weatherRepository: WeatherRepository,
        private val getUserUseCase: GetUserUseCase,
    ) {
        suspend fun run(params: Params): Either<Failure, Unit> {
            val userResult = getUserUseCase.run(Unit).first()
            if (userResult is Either.Left) {
                return userResult
            }
            val user = (userResult as Either.Right).b

            val geocoderResult = geocoderRepository.getFromLocation(params.lat, params.lon)
            if (geocoderResult is Either.Left) {
                return geocoderResult
            }
            val (city, country) = (geocoderResult as Either.Right).b

            return when (val weatherResult = weatherRepository.fetchCurrentWeather(params.lat, params.lon)) {
                is Either.Left -> weatherResult
                is Either.Right -> {
                    val weather =
                        weatherResult.b.copy(
                            userEmail = user.email.value,
                            city = city,
                            country = country,
                        )
                    weatherRepository.saveWeather(weather)
                }
            }
        }

        data class Params(
            val lat: Double,
            val lon: Double,
        )
    }
