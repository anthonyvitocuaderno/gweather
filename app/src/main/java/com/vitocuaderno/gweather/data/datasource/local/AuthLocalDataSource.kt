package com.vitocuaderno.gweather.data.datasource.local

import com.vitocuaderno.gweather.data.datasource.local.entity.UserEntityLocal
import kotlinx.coroutines.flow.Flow

interface AuthLocalDataSource {
    suspend fun register(user: UserEntityLocal)

    fun login(
        email: String,
        password: String,
    ): Flow<UserEntityLocal?>

    suspend fun logout()

    fun getLoggedInUser(): Flow<UserEntityLocal?>

    suspend fun isUserExisting(email: String): Boolean
}
