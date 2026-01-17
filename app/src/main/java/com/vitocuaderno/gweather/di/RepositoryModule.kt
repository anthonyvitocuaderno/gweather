package com.vitocuaderno.gweather.di

import com.vitocuaderno.gweather.data.repository.AuthRepositoryImpl
import com.vitocuaderno.gweather.data.repository.WeatherRepositoryImpl
import com.vitocuaderno.gweather.domain.repository.AuthRepository
import com.vitocuaderno.gweather.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(weatherRepositoryImpl: WeatherRepositoryImpl): WeatherRepository
}
