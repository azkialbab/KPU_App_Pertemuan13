package com.example.kpu

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.kpu.database.AppDatabase
import com.example.kpu.databinding.ActivityMainBinding // Perbaiki nama binding menjadi ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: AppDatabase
    private lateinit var preferenceHelper: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "app_db").build()
        preferenceHelper = PrefManager(this)

        binding.btnMasuk.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

}
