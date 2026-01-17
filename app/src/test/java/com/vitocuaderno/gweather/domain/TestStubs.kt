package com.vitocuaderno.gweather.domain

import com.vitocuaderno.gweather.core.exceptions.Either
import com.vitocuaderno.gweather.domain.valueobjects.Email
import com.vitocuaderno.gweather.domain.valueobjects.Password

object TestStubs {
    val testEmail = (Email.create("test@email.com") as Either.Right).b
    val testPassword = (Password.create("Password123") as Either.Right).b
}
