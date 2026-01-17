package com.vitocuaderno.gweather.di

import android.content.Context
import android.location.Geocoder
import com.vitocuaderno.gweather.data.repository.GeocoderRepositoryImpl
import com.vitocuaderno.gweather.domain.repository.GeocoderRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.Locale
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class GeocoderModule {
    @Binds
    @Singleton
    abstract fun bindGeocoderRepository(geocoderRepositoryImpl: GeocoderRepositoryImpl): GeocoderRepository

    companion object {
        @Provides
        @Singleton
        fun provideGeocoder(
            @ApplicationContext context: Context,
        ): Geocoder = Geocoder(context, Locale.getDefault())
    }
}
