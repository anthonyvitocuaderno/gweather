package com.vitocuaderno.gweather.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.vitocuaderno.gweather.domain.FakeAuthRepository
import com.vitocuaderno.gweather.domain.TestStubs.testEmail
import com.vitocuaderno.gweather.domain.TestStubs.testPassword
import com.vitocuaderno.gweather.domain.model.User
import com.vitocuaderno.gweather.domain.usecase.user.GetUserUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetUserUseCaseTest {
    private lateinit var getUserUseCase: GetUserUseCase
    private lateinit var fakeAuthRepository: FakeAuthRepository

    @Before
    fun setUp() {
        fakeAuthRepository = FakeAuthRepository()
        getUserUseCase = GetUserUseCase(fakeAuthRepository)
    }

    @Test
    fun `Get user when logged in, returns user`() =
        runTest {
            val user = User(testEmail)
            fakeAuthRepository.register(user, testPassword)
            fakeAuthRepository.login(testEmail, testPassword).first()

            val result = getUserUseCase.run(Unit).first()

            assertThat(result.isRight).isTrue()
        }

    @Test
    fun `Get user when not logged in, returns failure`() =
        runTest {
            val result = getUserUseCase.run(Unit).first()

            assertThat(result.isLeft).isTrue()
        }
}
