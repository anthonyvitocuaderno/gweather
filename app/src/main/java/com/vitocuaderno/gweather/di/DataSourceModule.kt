package com.vitocuaderno.gweather.di

import com.vitocuaderno.gweather.data.datasource.local.AuthLocalDataSource
import com.vitocuaderno.gweather.data.datasource.local.AuthLocalDataSourceImpl
import com.vitocuaderno.gweather.data.datasource.local.WeatherLocalDataSource
import com.vitocuaderno.gweather.data.datasource.local.WeatherLocalDataSourceImpl
import com.vitocuaderno.gweather.data.datasource.remote.AuthRemoteDataSource
import com.vitocuaderno.gweather.data.datasource.remote.AuthRemoteDataSourceImpl
import com.vitocuaderno.gweather.data.datasource.remote.WeatherRemoteDataSource
import com.vitocuaderno.gweather.data.datasource.remote.WeatherRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    @Singleton
    abstract fun bindAuthLocalDataSource(authLocalDataSourceImpl: AuthLocalDataSourceImpl): AuthLocalDataSource

    @Binds
    @Singleton
    abstract fun bindAuthRemoteDataSource(authRemoteDataSourceImpl: AuthRemoteDataSourceImpl): AuthRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindWeatherLocalDataSource(weatherLocalDataSourceImpl: WeatherLocalDataSourceImpl): WeatherLocalDataSource

    @Binds
    @Singleton
    abstract fun bindWeatherRemoteDataSource(weatherRemoteDataSourceImpl: WeatherRemoteDataSourceImpl): WeatherRemoteDataSource
}
