package com.vitocuaderno.gweather.domain.usecase.location

import com.vitocuaderno.gweather.domain.platform.LocationProvider
import javax.inject.Inject

class IsLocationEnabled
    @Inject
    constructor(
        private val locationProvider: LocationProvider,
    ) {
        operator fun invoke(): Boolean = locationProvider.isLocationEnabled()
    }
