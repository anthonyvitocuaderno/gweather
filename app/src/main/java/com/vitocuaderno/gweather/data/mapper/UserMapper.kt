package com.vitocuaderno.gweather.data.mapper

import com.vitocuaderno.gweather.core.exceptions.Either
import com.vitocuaderno.gweather.core.exceptions.Failure
import com.vitocuaderno.gweather.data.datasource.local.entity.UserEntityLocal
import com.vitocuaderno.gweather.domain.model.User
import com.vitocuaderno.gweather.domain.valueobjects.Email

fun UserEntityLocal.toDomain(): Either<Failure, User> {
    val email = Email.create(this.email)
    return email.fold(
        { Either.Left(it) },
        { Either.Right(User(it)) },
    ) as Either<Failure, User>
}

fun User.toEntity(): UserEntityLocal = UserEntityLocal(email = email.value, encryptedPassword = "")
