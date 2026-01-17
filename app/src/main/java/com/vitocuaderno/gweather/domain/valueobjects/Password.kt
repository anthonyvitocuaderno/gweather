package com.vitocuaderno.gweather.domain.valueobjects

import com.vitocuaderno.gweather.core.exceptions.Either
import com.vitocuaderno.gweather.core.exceptions.Failure
import com.vitocuaderno.gweather.core.utils.Validators

@JvmInline
value class Password private constructor(
    val value: String,
) {
    companion object {
        fun create(password: String): Either<Failure, Password> =
            if (Validators.isValidPassword(password)) {
                Either.Right(Password(password))
            } else {
                Either.Left(Failure.InvalidPassword)
            }
    }
}
