package com.vitocuaderno.gweather.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.vitocuaderno.gweather.domain.FakeAuthRepository
import com.vitocuaderno.gweather.domain.TestStubs.testEmail
import com.vitocuaderno.gweather.domain.TestStubs.testPassword
import com.vitocuaderno.gweather.domain.model.User
import com.vitocuaderno.gweather.domain.usecase.user.LoginUserUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class LoginUserUseCaseTest {
    private lateinit var loginUserUseCase: LoginUserUseCase
    private lateinit var fakeAuthRepository: FakeAuthRepository

    @Before
    fun setUp() {
        fakeAuthRepository = FakeAuthRepository()
        loginUserUseCase = LoginUserUseCase(fakeAuthRepository)
    }

    @Test
    fun `Login user with valid credentials, returns success`() =
        runTest {
            val user = User(testEmail)
            fakeAuthRepository.register(user, testPassword)

            val result =
                loginUserUseCase
                    .run(
                        LoginUserUseCase.Params(
                            email = testEmail.value,
                            password = testPassword.value,
                        ),
                    ).first()

            assertThat(result.isRight).isTrue()
        }

    @Test
    fun `Login user with invalid credentials, returns failure`() =
        runTest {
            val result =
                loginUserUseCase
                    .run(
                        LoginUserUseCase.Params(
                            email = testEmail.value,
                            password = testPassword.value,
                        ),
                    ).first()

            assertThat(result.isLeft).isTrue()
        }
}
