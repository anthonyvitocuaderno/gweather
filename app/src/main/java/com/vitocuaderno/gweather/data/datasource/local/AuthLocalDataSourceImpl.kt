package com.vitocuaderno.gweather.data.datasource.local

import com.vitocuaderno.gweather.data.datasource.local.entity.UserEntityLocal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthLocalDataSourceImpl
    @Inject
    constructor(
        private val userDao: UserDao,
        private val sessionManager: SessionManager,
    ) : AuthLocalDataSource {
        override suspend fun register(user: UserEntityLocal) {
            userDao.insertUser(user)
        }

        override fun login(
            email: String,
            password: String,
        ): Flow<UserEntityLocal?> =
            userDao.getUserByEmail(email).map { user ->
                if (user != null && user.encryptedPassword == password) {
                    sessionManager.saveSession(email)
                    user
                } else {
                    null
                }
            }

        override suspend fun logout() {
            sessionManager.clearSession()
        }

        override fun getLoggedInUser(): Flow<UserEntityLocal?> {
            val email = sessionManager.getSession()
            return if (email != null) {
                userDao.getUserByEmail(email)
            } else {
                // Return a flow that emits null if no session is found
                object : Flow<UserEntityLocal?> {
                    override suspend fun collect(collector: kotlinx.coroutines.flow.FlowCollector<UserEntityLocal?>) {
                        collector.emit(null)
                    }
                }
            }
        }

        override suspend fun isUserExisting(email: String): Boolean = userDao.isUserExisting(email)
    }
