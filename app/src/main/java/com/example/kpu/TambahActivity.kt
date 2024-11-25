package com.example.kpu

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.kpu.database.AppDatabase
import com.example.kpu.database.Pemilih
import com.example.kpu.databinding.ActivityTambahBinding
import kotlinx.coroutines.launch

class TambahActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTambahBinding
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "app_db").build()

        binding.tsave.setOnClickListener {
            savePemilih()
        }
    }

    private fun savePemilih() {
        val name = binding.tname.text.toString()
        val nik = binding.tnik.text.toString().toLong()
        val gender = if (binding.tMale.isChecked) "Laki-laki" else "Perempuan"
        val address = binding.talamat.text.toString()

        val pemilih = Pemilih(name = name, nik = nik, gender = gender, alamat = address)

        lifecycleScope.launch {
            db.pemilihDao().insert(pemilih)
            startActivity(Intent(this@TambahActivity, HomeActivity::class.java))
            finish()
        }
    }
}