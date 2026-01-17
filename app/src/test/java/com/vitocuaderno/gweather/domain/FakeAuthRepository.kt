package com.vitocuaderno.gweather.domain

import com.vitocuaderno.gweather.core.exceptions.Either
import com.vitocuaderno.gweather.core.exceptions.Failure
import com.vitocuaderno.gweather.domain.model.User
import com.vitocuaderno.gweather.domain.repository.AuthRepository
import com.vitocuaderno.gweather.domain.valueobjects.Email
import com.vitocuaderno.gweather.domain.valueobjects.Password
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeAuthRepository : AuthRepository {
    private val users = mutableListOf<User>()
    private var loggedInUser: User? = null

    override suspend fun register(
        user: User,
        password: Password,
    ): Either<Failure, Unit> {
        if (users.any {
                it.email.value == user.email.value
            }
        ) {
            return Either.Left(Failure.EmailAlreadyExists)
        }
        users.add(user)
        return Either.Right(Unit)
    }

    override fun login(
        email: Email,
        password: Password,
    ): Flow<Either<Failure, User>> =
        flow {
            val user = users.find { it.email.value == email.value }
            if (user != null) {
                loggedInUser = user
                emit(Either.Right(user))
            } else {
                emit(Either.Left(Failure.ServerError)) // Simulate a login failure
            }
        }

    override suspend fun logout(): Either<Failure, Unit> {
        loggedInUser = null
        return Either.Right(Unit)
    }

    override fun getLoggedInUser(): Flow<Either<Failure, User>> =
        flow {
            if (loggedInUser != null) {
                emit(Either.Right(loggedInUser!!))
            } else {
                emit(Either.Left(Failure.ServerError)) // Simulate no user logged in
            }
        }
}
