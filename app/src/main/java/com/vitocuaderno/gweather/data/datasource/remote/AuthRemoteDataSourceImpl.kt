package com.vitocuaderno.gweather.data.datasource.remote

import com.vitocuaderno.gweather.data.datasource.remote.entity.UserEntityRemote
import javax.inject.Inject

class AuthRemoteDataSourceImpl
    @Inject
    constructor(
        private val apiClient: ApiClient,
    ) : AuthRemoteDataSource {
        override suspend fun register(
            user: UserEntityRemote,
            password: String,
        ) {
            // TODO: Implement remote registration
        }

        override suspend fun login(
            email: String,
            password: String,
        ): UserEntityRemote? {
            // TODO: Implement remote login
            return null
        }

        override suspend fun logout() {
            // TODO: Implement remote logout
        }

        override suspend fun getLoggedInUser(): UserEntityRemote? {
            // TODO: Implement remote getLoggedInUser
            return null
        }
    }
