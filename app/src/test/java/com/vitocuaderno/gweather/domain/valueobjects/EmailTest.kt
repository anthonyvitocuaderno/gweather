package com.vitocuaderno.gweather.domain.valueobjects

import com.google.common.truth.Truth.assertThat
import com.vitocuaderno.gweather.domain.TestStubs.testEmail
import org.junit.Test

class EmailTest {
    @Test
    fun `Valid email, returns email`() {
        val result = Email.create(testEmail.value)
        assertThat(result.isRight).isTrue()
    }

    @Test
    fun `Invalid email, returns failure`() {
        val email = "invalid-email"
        val result = Email.create(email)
        assertThat(result.isLeft).isTrue()
    }

    @Test
    fun `Empty email, returns failure`() {
        val email = ""
        val result = Email.create(email)
        assertThat(result.isLeft).isTrue()
    }
}
