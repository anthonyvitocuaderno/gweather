package com.vitocuaderno.gweather.data.datasource.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vitocuaderno.gweather.data.datasource.local.entity.WeatherEntityLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherEntityLocal)

    @Query("SELECT * FROM weathers WHERE user_email = :userEmail ORDER BY created_at DESC LIMIT 1")
    fun getLatestWeather(userEmail: String): Flow<WeatherEntityLocal?>

    @Query("SELECT * FROM weathers WHERE user_email = :userEmail ORDER BY created_at DESC")
    fun getAllWeathers(userEmail: String): PagingSource<Int, WeatherEntityLocal>
}
