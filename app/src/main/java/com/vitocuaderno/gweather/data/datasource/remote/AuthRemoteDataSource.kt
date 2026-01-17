package com.vitocuaderno.gweather.data.datasource.remote

import com.vitocuaderno.gweather.data.datasource.remote.entity.UserEntityRemote

interface AuthRemoteDataSource {
    suspend fun register(
        user: UserEntityRemote,
        password: String,
    )

    suspend fun login(
        email: String,
        password: String,
    ): UserEntityRemote?

    suspend fun logout()

    suspend fun getLoggedInUser(): UserEntityRemote?
}
