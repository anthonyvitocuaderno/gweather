package com.vitocuaderno.gweather.domain.usecase.location

import com.google.common.truth.Truth.assertThat
import com.vitocuaderno.gweather.domain.platform.FakeLocationProvider
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class LocationUseCasesTest {
    private lateinit var locationProvider: FakeLocationProvider
    private lateinit var isLocationEnabled: IsLocationEnabled
    private lateinit var requestLocationEnable: RequestLocationEnable
    private lateinit var checkLocationPermission: CheckLocationPermission
    private lateinit var requestLocationPermission: RequestLocationPermission
    private lateinit var getCurrentLocation: GetCurrentLocation

    @Before
    fun setUp() {
        locationProvider = FakeLocationProvider()
        isLocationEnabled = IsLocationEnabled(locationProvider)
        requestLocationEnable = RequestLocationEnable(locationProvider)
        checkLocationPermission = CheckLocationPermission(locationProvider)
        requestLocationPermission = RequestLocationPermission(locationProvider)
        getCurrentLocation = GetCurrentLocation(locationProvider)
    }

    @Test
    fun `Location is enabled, returns true`() {
        locationProvider.setLocationEnabled(true)
        val result = isLocationEnabled.invoke()
        assertThat(result).isTrue()
    }

    @Test
    fun `Location is disabled, returns false`() {
        locationProvider.setLocationEnabled(false)
        val result = isLocationEnabled.invoke()
        assertThat(result).isFalse()
    }

    @Test
    fun `Permission is granted, returns true`() {
        locationProvider.setPermissionGranted(true)
        val result = checkLocationPermission.invoke()
        assertThat(result).isTrue()
    }

    @Test
    fun `Permission is not granted, returns false`() {
        locationProvider.setPermissionGranted(false)
        val result = checkLocationPermission.invoke()
        assertThat(result).isFalse()
    }

    @Test
    fun `Get current location, returns location`() =
        runTest {
            val result = getCurrentLocation.invoke().first()
            assertThat(result.isRight).isTrue()
        }

    @Test
    fun `Get current location when permission not granted, returns failure`() =
        runTest {
            locationProvider.setPermissionGranted(false)
            val result = getCurrentLocation.invoke().first()
            assertThat(result.isLeft).isTrue()
        }

    @Test
    fun `Get current location when location not enabled, returns failure`() =
        runTest {
            locationProvider.setLocationEnabled(false)
            val result = getCurrentLocation.invoke().first()
            assertThat(result.isLeft).isTrue()
        }

    @Test
    fun `Get current location when location not found, returns failure`() =
        runTest {
            locationProvider.setLocation(null)
            val result = getCurrentLocation.invoke().first()
            assertThat(result.isLeft).isTrue()
        }
}
