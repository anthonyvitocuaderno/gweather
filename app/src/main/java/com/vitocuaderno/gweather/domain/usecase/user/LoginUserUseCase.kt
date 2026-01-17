package com.vitocuaderno.gweather.domain.usecase.user

import com.vitocuaderno.gweather.core.base.BaseUseCase
import com.vitocuaderno.gweather.core.exceptions.Either
import com.vitocuaderno.gweather.core.exceptions.Failure
import com.vitocuaderno.gweather.domain.model.User
import com.vitocuaderno.gweather.domain.repository.AuthRepository
import com.vitocuaderno.gweather.domain.valueobjects.Email
import com.vitocuaderno.gweather.domain.valueobjects.Password
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUserUseCase
    @Inject
    constructor(
        private val authRepository: AuthRepository,
    ) : BaseUseCase<User, LoginUserUseCase.Params> {
        override fun run(params: Params): Flow<Either<Failure, User>> {
            val email = Email.create(params.email)
            val password = Password.create(params.password)

            return if (email.isLeft) {
                flow { emit(Either.Left(Failure.InvalidEmail)) }
            } else if (password.isLeft) {
                flow { emit(Either.Left(Failure.InvalidPassword)) }
            } else {
                authRepository.login(email.get(), password.get())
            }
        }

        data class Params(
            val email: String,
            val password: String,
        )

        // Helper to get the value from Either.Right, assuming it is Right.
        private fun <T> Either<*, T>.get() = (this as Either.Right).b
    }
