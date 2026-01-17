package com.vitocuaderno.gweather.core.utils

import java.util.regex.Pattern

object Validators {
    private val EMAIL_ADDRESS_PATTERN =
        Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+",
        )

    fun isValidEmail(email: String): Boolean = EMAIL_ADDRESS_PATTERN.matcher(email).matches()

    fun isValidPassword(password: String): Boolean {
        // Example: at least 8 characters, one digit, one uppercase letter
        return password.length >= 8 && password.any { it.isDigit() } && password.any { it.isUpperCase() }
    }
}
