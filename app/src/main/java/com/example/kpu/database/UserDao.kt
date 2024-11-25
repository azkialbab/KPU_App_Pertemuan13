package com.example.kpu.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: Users)

    @Query("SELECT * FROM users WHERE username = :username AND password = :password LIMIT 1")
    suspend fun login(username: String, password: String): Users?

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<Users>
}
