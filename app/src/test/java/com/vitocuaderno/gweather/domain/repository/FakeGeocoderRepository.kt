package com.vitocuaderno.gweather.domain.repository

import com.vitocuaderno.gweather.core.exceptions.Either
import com.vitocuaderno.gweather.core.exceptions.Failure

class FakeGeocoderRepository : GeocoderRepository {
    private var shouldReturnError = false

    fun setShouldReturnError(value: Boolean) {
        shouldReturnError = value
    }

    override suspend fun getFromLocation(
        lat: Double,
        lon: Double,
    ): Either<Failure, Pair<String, String>> =
        if (shouldReturnError) {
            Either.Left(Failure.ServerError)
        } else {
            Either.Right(Pair("Fake City", "FC"))
        }
}
