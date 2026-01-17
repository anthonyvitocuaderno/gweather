package com.vitocuaderno.gweather.data.datasource.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "weathers",
    foreignKeys = [
        ForeignKey(
            entity = UserEntityLocal::class,
            parentColumns = ["email"],
            childColumns = ["user_email"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
data class WeatherEntityLocal(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "user_email", index = true)
    val userEmail: String,
    val city: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val celsius: Double,
    val sunrise: Long,
    val sunset: Long,
    val weatherIcon: String,
    @ColumnInfo(name = "created_at")
    val createdAt: Long,
)
