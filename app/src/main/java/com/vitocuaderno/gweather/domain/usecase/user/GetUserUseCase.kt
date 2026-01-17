package com.vitocuaderno.gweather.domain.usecase.user

import com.vitocuaderno.gweather.core.base.BaseUseCase
import com.vitocuaderno.gweather.core.exceptions.Either
import com.vitocuaderno.gweather.core.exceptions.Failure
import com.vitocuaderno.gweather.domain.model.User
import com.vitocuaderno.gweather.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserUseCase
    @Inject
    constructor(
        private val authRepository: AuthRepository,
    ) : BaseUseCase<User, Unit> {
        override fun run(params: Unit): Flow<Either<Failure, User>> = authRepository.getLoggedInUser()
    }
