package com.example.kpu

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.kpu.database.AppDatabase
import com.example.kpu.database.Pemilih
import com.example.kpu.databinding.ActivityEditBinding
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditBinding
    private lateinit var db: AppDatabase
    private var pemilihId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "app_db").build()

        pemilihId = intent.getLongExtra("PEMILIH_ID", 0)
        loadPemilihData()

        binding.esave.setOnClickListener {
            updatePemilih()
        }
    }

    private fun loadPemilihData() {
        lifecycleScope.launch {
            val pemilih = db.pemilihDao().getAllPemilih().find { it.id == pemilihId }
            pemilih?.let {
                binding.ename.setText(it.name)
                binding.enik.setText(it.nik.toString())
                if (it.gender == "Laki-laki") {
                    binding.eMale.isChecked = true
                } else {
                    binding.eFemale.isChecked = true
                }
                binding.ealamat.setText(it.alamat)
            }
        }
    }

    private fun updatePemilih() {
        val name = binding.ename.text.toString()
        val nik = binding.enik.text.toString().toLongOrNull()
        val gender = if (binding.eMale.isChecked) "Laki-laki" else "Perempuan"
        val alamat = binding.ealamat.text.toString()

        if (nik == null) {
            binding.enik.error = "NIK harus berupa angka"
            return
        }

        val pemilih = Pemilih(id = pemilihId, name = name, nik = nik, gender = gender, alamat = alamat)

        lifecycleScope.launch {
            db.pemilihDao().update(pemilih)
            startActivity(Intent(this@EditActivity, HomeActivity::class.java))
            finish()
        }
    }
}
