package com.example.kpu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.kpu.database.AppDatabase
import com.example.kpu.databinding.ActivityDetailBinding
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var db: AppDatabase
    private var pemilihId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "app_db").build()

        pemilihId = intent.getLongExtra("PEMILIH_ID", 0)
        loadPemilihData()

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun loadPemilihData() {
        lifecycleScope.launch {
            val pemilih = db.pemilihDao().getAllPemilih().find { it.id == pemilihId }
            pemilih?.let {
                binding.rusername.setText(it.name)
                binding.tnik.setText(it.nik.toString())
                binding.rGender.setText(it.gender)
                binding.ralamat.setText(it.alamat)


                setFieldsReadOnly()
            }
        }
    }

    private fun setFieldsReadOnly() {
        binding.rusername.apply {
            isFocusable = false
            isFocusableInTouchMode = false
            isEnabled = false
        }

        binding.tnik.apply {
            isFocusable = false
            isFocusableInTouchMode = false
            isEnabled = false
        }

        binding.rGender.apply {
            isFocusable = false
            isFocusableInTouchMode = false
            isEnabled = false
        }

        binding.ralamat.apply {
            isFocusable = false
            isFocusableInTouchMode = false
            isEnabled = false
        }
    }
}
