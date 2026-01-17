package com.vitocuaderno.gweather.domain.platform

import com.vitocuaderno.gweather.core.exceptions.Either
import com.vitocuaderno.gweather.core.exceptions.Failure
import com.vitocuaderno.gweather.domain.model.Location
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeLocationProvider : LocationProvider {
    private var isLocationEnabled = true
    private var isPermissionGranted = true
    private var location: Location? = Location(0.0, 0.0)

    fun setLocationEnabled(isEnabled: Boolean) {
        isLocationEnabled = isEnabled
    }

    fun setPermissionGranted(isGranted: Boolean) {
        isPermissionGranted = isGranted
    }

    fun setLocation(location: Location?) {
        this.location = location
    }

    override fun isLocationEnabled(): Boolean = isLocationEnabled

    override fun requestLocationEnable() {
        // No-op
    }

    override fun checkPermissions(): Boolean = isPermissionGranted

    override fun requestPermissions() {
        // No-op
    }

    override fun getCurrentLocation(): Flow<Either<Failure, Location>> =
        flow {
            if (!isPermissionGranted) {
                emit(Either.Left(Failure.LocationPermissionNotGranted))
                return@flow
            }
            if (!isLocationEnabled) {
                emit(Either.Left(Failure.LocationServiceNotEnabled))
                return@flow
            }
            if (location == null) {
                emit(Either.Left(Failure.LocationNotFound))
                return@flow
            }
            emit(Either.Right(location!!))
        }
}
