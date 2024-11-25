package com.example.kpu.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Users::class, Pemilih::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun pemilihDao(): PemilihDao
}
