package com.vitocuaderno.gweather.data.datasource.remote

import com.vitocuaderno.gweather.data.datasource.remote.entity.UserEntityRemote
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiClient {
    @POST("auth/register")
    suspend fun register(
        @Body request: UserEntityRemote,
    ): UserEntityRemote
}
