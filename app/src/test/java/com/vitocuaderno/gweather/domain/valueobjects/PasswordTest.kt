package com.vitocuaderno.gweather.domain.valueobjects

import com.google.common.truth.Truth.assertThat
import com.vitocuaderno.gweather.domain.TestStubs.testPassword
import org.junit.Test

class PasswordTest {
    @Test
    fun `Valid password, returns password`() {
        val result = Password.create(testPassword.value)
        assertThat(result.isRight).isTrue()
    }

    @Test
    fun `Invalid password, returns failure`() {
        val password = "password"
        val result = Password.create(password)
        assertThat(result.isLeft).isTrue()
    }

    @Test
    fun `Empty password, returns failure`() {
        val password = ""
        val result = Password.create(password)
        assertThat(result.isLeft).isTrue()
    }
}
