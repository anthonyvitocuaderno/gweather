package com.vitocuaderno.gweather.core.base

import com.vitocuaderno.gweather.core.exceptions.Either
import com.vitocuaderno.gweather.core.exceptions.Failure
import kotlinx.coroutines.flow.Flow

interface BaseUseCase<out Type, in Params> where Type : Any {
    fun run(params: Params): Flow<Either<Failure, Type>>
}
