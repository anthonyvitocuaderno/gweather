package com.vitocuaderno.gweather.domain.usecase.location

import com.vitocuaderno.gweather.core.exceptions.Either
import com.vitocuaderno.gweather.core.exceptions.Failure
import com.vitocuaderno.gweather.domain.model.Location
import com.vitocuaderno.gweather.domain.platform.LocationProvider
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentLocation
    @Inject
    constructor(
        private val locationProvider: LocationProvider,
    ) {
        operator fun invoke(): Flow<Either<Failure, Location>> = locationProvider.getCurrentLocation()
    }
