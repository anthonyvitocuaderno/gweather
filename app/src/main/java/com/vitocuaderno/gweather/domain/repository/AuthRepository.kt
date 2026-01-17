package com.vitocuaderno.gweather.domain.repository

import com.vitocuaderno.gweather.core.base.BaseRepository
import com.vitocuaderno.gweather.core.exceptions.Either
import com.vitocuaderno.gweather.core.exceptions.Failure
import com.vitocuaderno.gweather.domain.model.User
import com.vitocuaderno.gweather.domain.valueobjects.Email
import com.vitocuaderno.gweather.domain.valueobjects.Password
import kotlinx.coroutines.flow.Flow

interface AuthRepository : BaseRepository {
    suspend fun register(
        user: User,
        password: Password,
    ): Either<Failure, Unit>

    fun login(
        email: Email,
        password: Password,
    ): Flow<Either<Failure, User>>

    suspend fun logout(): Either<Failure, Unit>

    fun getLoggedInUser(): Flow<Either<Failure, User>>
}
