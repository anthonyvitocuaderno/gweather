package com.vitocuaderno.gweather.di

import android.content.Context
import com.vitocuaderno.gweather.domain.platform.LocationProvider
import com.vitocuaderno.gweather.platform.LocationProviderImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PlatformModule {
    @Binds
    @Singleton
    abstract fun bindLocationProvider(locationProviderImpl: LocationProviderImpl): LocationProvider

    companion object {
        @Provides
        @Singleton
        fun provideLocationProviderImpl(
            @ApplicationContext context: Context,
        ): LocationProviderImpl = LocationProviderImpl(context)
    }
}
