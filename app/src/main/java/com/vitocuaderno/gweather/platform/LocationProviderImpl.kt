package com.vitocuaderno.gweather.platform

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.provider.Settings
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.vitocuaderno.gweather.core.exceptions.Either
import com.vitocuaderno.gweather.core.exceptions.Failure
import com.vitocuaderno.gweather.domain.model.Location
import com.vitocuaderno.gweather.domain.platform.LocationProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LocationProviderImpl
    @Inject
    constructor(
        private val context: Context,
    ) : LocationProvider {
        private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        override fun isLocationEnabled(): Boolean {
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        }

        override fun requestLocationEnable() {
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }

        override fun checkPermissions(): Boolean =
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION,
            ) == PackageManager.PERMISSION_GRANTED

        override fun requestPermissions() {
            // Handled in the UI layer
        }

        override fun getCurrentLocation(): Flow<Either<Failure, Location>> =
            flow {
                if (!checkPermissions()) {
                    emit(Either.Left(Failure.LocationPermissionNotGranted))
                    return@flow
                }

                if (!isLocationEnabled()) {
                    emit(Either.Left(Failure.LocationServiceNotEnabled))
                    return@flow
                }

                try {
                    val result =
                        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null).await()
                            ?: fusedLocationClient.lastLocation.await()

                    if (result != null) {
                        emit(Either.Right(Location(result.latitude, result.longitude)))
                    } else {
                        emit(Either.Left(Failure.LocationNotFound))
                    }
                } catch (e: SecurityException) {
                    emit(Either.Left(Failure.LocationPermissionNotGranted))
                } catch (e: Exception) {
                    emit(Either.Left(Failure.ServerError))
                }
            }
    }
