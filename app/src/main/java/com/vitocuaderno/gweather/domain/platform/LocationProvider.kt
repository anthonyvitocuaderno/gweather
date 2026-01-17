package com.vitocuaderno.gweather.domain.platform

import com.vitocuaderno.gweather.core.exceptions.Either
import com.vitocuaderno.gweather.core.exceptions.Failure
import com.vitocuaderno.gweather.domain.model.Location
import kotlinx.coroutines.flow.Flow

interface LocationProvider {
    fun isLocationEnabled(): Boolean

    fun requestLocationEnable()

    fun checkPermissions(): Boolean

    fun requestPermissions()

    fun getCurrentLocation(): Flow<Either<Failure, Location>>
}
