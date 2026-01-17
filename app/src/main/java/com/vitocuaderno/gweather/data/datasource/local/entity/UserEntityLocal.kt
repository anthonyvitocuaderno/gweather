package com.vitocuaderno.gweather.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntityLocal(
    @PrimaryKey
    val email: String,
    val encryptedPassword: String,
)
