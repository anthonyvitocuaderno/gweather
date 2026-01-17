package com.vitocuaderno.gweather.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vitocuaderno.gweather.data.datasource.local.entity.UserEntityLocal
import com.vitocuaderno.gweather.data.datasource.local.entity.WeatherEntityLocal

@Database(entities = [UserEntityLocal::class, WeatherEntityLocal::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    abstract fun weatherDao(): WeatherDao
}
