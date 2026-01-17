package com.vitocuaderno.gweather.domain.valueobjects

import com.vitocuaderno.gweather.core.exceptions.Either
import com.vitocuaderno.gweather.core.exceptions.Failure
import com.vitocuaderno.gweather.core.utils.Validators

@JvmInline
value class Email private constructor(
    val value: String,
) {
    companion object {
        fun create(email: String): Either<Failure, Email> =
            if (Validators.isValidEmail(email)) {
                Either.Right(Email(email))
            } else {
                Either.Left(Failure.InvalidEmail)
            }
    }
}
