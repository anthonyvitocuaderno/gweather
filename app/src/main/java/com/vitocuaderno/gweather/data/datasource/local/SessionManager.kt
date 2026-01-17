package com.vitocuaderno.gweather.data.datasource.local

import android.content.SharedPreferences
import javax.inject.Inject

class SessionManager
    @Inject
    constructor(
        private val prefs: SharedPreferences,
    ) {
        companion object {
            private const val KEY_USER_EMAIL = "user_email"
        }

        fun saveSession(email: String) {
            prefs.edit().putString(KEY_USER_EMAIL, email).apply()
        }

        fun getSession(): String? = prefs.getString(KEY_USER_EMAIL, null)

        fun clearSession() {
            prefs.edit().remove(KEY_USER_EMAIL).apply()
        }
    }
