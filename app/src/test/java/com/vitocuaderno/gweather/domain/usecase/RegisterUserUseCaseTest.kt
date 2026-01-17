package com.vitocuaderno.gweather.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.vitocuaderno.gweather.domain.FakeAuthRepository
import com.vitocuaderno.gweather.domain.TestStubs.testEmail
import com.vitocuaderno.gweather.domain.TestStubs.testPassword
import com.vitocuaderno.gweather.domain.model.User
import com.vitocuaderno.gweather.domain.usecase.user.RegisterUserUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class RegisterUserUseCaseTest {
    private lateinit var registerUserUseCase: RegisterUserUseCase
    private lateinit var fakeAuthRepository: FakeAuthRepository

    @Before
    fun setUp() {
        fakeAuthRepository = FakeAuthRepository()
        registerUserUseCase = RegisterUserUseCase(fakeAuthRepository)
    }

    @Test
    fun `Register user with valid data, returns success`() =
        runTest {
            val result =
                registerUserUseCase
                    .run(
                        RegisterUserUseCase.Params(
                            email = testEmail.value,
                            password = testPassword.value,
                        ),
                    ).first()

            assertThat(result.isRight).isTrue()
        }

    @Test
    fun `Register user with existing email, returns failure`() =
        runTest {
            val user = User(testEmail)
            fakeAuthRepository.register(user, testPassword)

            val result =
                registerUserUseCase
                    .run(
                        RegisterUserUseCase.Params(
                            email = testEmail.value,
                            password = testPassword.value,
                        ),
                    ).first()

            assertThat(result.isLeft).isTrue()
        }
}
