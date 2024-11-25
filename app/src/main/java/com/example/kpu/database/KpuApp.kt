package com.example.kpu

import android.app.Application
import androidx.room.Room
import com.example.kpu.database.AppDatabase

class KpuApp : Application() {
    lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "kpu-database"
        ).build()
    }
}
