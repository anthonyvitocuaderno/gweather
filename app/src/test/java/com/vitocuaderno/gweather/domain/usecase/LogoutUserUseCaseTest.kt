package com.vitocuaderno.gweather.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.vitocuaderno.gweather.domain.FakeAuthRepository
import com.vitocuaderno.gweather.domain.usecase.user.LogoutUserUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class LogoutUserUseCaseTest {
    private lateinit var logoutUserUseCase: LogoutUserUseCase
    private lateinit var fakeAuthRepository: FakeAuthRepository

    @Before
    fun setUp() {
        fakeAuthRepository = FakeAuthRepository()
        logoutUserUseCase = LogoutUserUseCase(fakeAuthRepository)
    }

    @Test
    fun `Logout user, returns success`() =
        runTest {
            val result = logoutUserUseCase.run(Unit).first()

            assertThat(result.isRight).isTrue()
        }
}
