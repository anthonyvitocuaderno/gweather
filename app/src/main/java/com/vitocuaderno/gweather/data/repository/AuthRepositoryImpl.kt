package com.vitocuaderno.gweather.data.repository

import com.vitocuaderno.gweather.core.exceptions.Either
import com.vitocuaderno.gweather.core.exceptions.Failure
import com.vitocuaderno.gweather.data.datasource.local.AuthLocalDataSource
import com.vitocuaderno.gweather.data.datasource.remote.AuthRemoteDataSource
import com.vitocuaderno.gweather.data.mapper.toDomain
import com.vitocuaderno.gweather.data.mapper.toEntity
import com.vitocuaderno.gweather.domain.model.User
import com.vitocuaderno.gweather.domain.repository.AuthRepository
import com.vitocuaderno.gweather.domain.valueobjects.Email
import com.vitocuaderno.gweather.domain.valueobjects.Password
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthRepositoryImpl
    @Inject
    constructor(
        private val localDataSource: AuthLocalDataSource,
        private val remoteDataSource: AuthRemoteDataSource,
    ) : AuthRepository {
        override suspend fun register(
            user: User,
            password: Password,
        ): Either<Failure, Unit> {
            // TODO: Implement remote registration
            if (localDataSource.isUserExisting(user.email.value)) {
                return Either.Left(Failure.EmailAlreadyExists)
            }
            val userEntity = user.toEntity().copy(encryptedPassword = password.value)
            localDataSource.register(userEntity)
            return Either.Right(Unit)
        }

        override fun login(
            email: Email,
            password: Password,
        ): Flow<Either<Failure, User>> {
            // TODO: Implement remote login and sync
            return localDataSource.login(email.value, password.value).map { userEntity ->
                userEntity?.toDomain() ?: Either.Left(Failure.ServerError) // Or a more specific failure
            }
        }

        override suspend fun logout(): Either<Failure, Unit> {
            // TODO: Implement remote logout
            localDataSource.logout()
            return Either.Right(Unit)
        }

        override fun getLoggedInUser(): Flow<Either<Failure, User>> {
            // TODO: Check remote session if local session is invalid
            return localDataSource.getLoggedInUser().map { userEntity ->
                userEntity?.toDomain() ?: Either.Left(Failure.ServerError) // Or a more specific failure
            }
        }
    }
