package com.vitocuaderno.gweather.data.repository

import android.location.Geocoder
import com.vitocuaderno.gweather.core.exceptions.Either
import com.vitocuaderno.gweather.core.exceptions.Failure
import com.vitocuaderno.gweather.domain.repository.GeocoderRepository
import java.io.IOException
import javax.inject.Inject

class GeocoderRepositoryImpl
    @Inject
    constructor(
        private val geocoder: Geocoder,
    ) : GeocoderRepository {
        override suspend fun getFromLocation(
            lat: Double,
            lon: Double,
        ): Either<Failure, Pair<String, String>> =
            try {
                val addresses = geocoder.getFromLocation(lat, lon, 1)
                if (addresses.isNullOrEmpty()) {
                    Either.Left(Failure.ServerError) // Or a more specific failure
                } else {
                    val address = addresses[0]
                    val city = address.locality
                    val country = address.countryName
                    Either.Right(Pair(city, country))
                }
            } catch (e: IOException) {
                Either.Left(Failure.NetworkConnection)
            }
    }
