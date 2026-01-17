package com.vitocuaderno.gweather.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vitocuaderno.gweather.data.datasource.local.entity.UserEntityLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntityLocal)

    @Query("SELECT * FROM users WHERE email = :email")
    fun getUserByEmail(email: String): Flow<UserEntityLocal?>

    @Query("SELECT EXISTS(SELECT * FROM users WHERE email = :email)")
    suspend fun isUserExisting(email: String): Boolean
}
