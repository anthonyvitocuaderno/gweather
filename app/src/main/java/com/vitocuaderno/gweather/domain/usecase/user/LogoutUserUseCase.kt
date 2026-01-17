package com.vitocuaderno.gweather.domain.usecase.user

import com.vitocuaderno.gweather.core.base.BaseUseCase
import com.vitocuaderno.gweather.core.exceptions.Either
import com.vitocuaderno.gweather.core.exceptions.Failure
import com.vitocuaderno.gweather.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LogoutUserUseCase
    @Inject
    constructor(
        private val authRepository: AuthRepository,
    ) : BaseUseCase<Unit, Unit> {
        override fun run(params: Unit): Flow<Either<Failure, Unit>> =
            flow {
                emit(authRepository.logout())
            }
    }
