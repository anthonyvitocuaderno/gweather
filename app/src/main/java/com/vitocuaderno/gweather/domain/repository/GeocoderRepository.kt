package com.vitocuaderno.gweather.domain.repository

import com.vitocuaderno.gweather.core.base.BaseRepository
import com.vitocuaderno.gweather.core.exceptions.Either
import com.vitocuaderno.gweather.core.exceptions.Failure

interface GeocoderRepository : BaseRepository {
    suspend fun getFromLocation(
        lat: Double,
        lon: Double,
    ): Either<Failure, Pair<String, String>>
}
