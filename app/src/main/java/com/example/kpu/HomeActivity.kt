package com.example.kpu

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.kpu.database.AppDatabase
import com.example.kpu.database.Pemilih
import com.example.kpu.databinding.ActivityHomeBinding
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var db: AppDatabase
    private lateinit var pemilihAdapter: PemilihAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "app_db").build()

        binding.hTambah.setOnClickListener {
            startActivity(Intent(this, TambahActivity::class.java))
            showToast("Navigating to Add Pemilih page")
        }

        binding.tLogout.setOnClickListener {

            finish()
            showToast("You have successfully logged out")
        }

        loadPemilihData()
    }

    private fun loadPemilihData() {
        lifecycleScope.launch {
            try {
                val pemilihList = db.pemilihDao().getAllPemilih()
                pemilihAdapter = PemilihAdapter(
                    pemilihList,
                    onDelete = { pemilih -> deletePemilih(pemilih) },
                    onEdit = { pemilih -> editPemilih(pemilih) },
                    onDetail = { pemilih -> showDetailPemilih(pemilih) }
                )

                binding.rvPemilih.apply {
                    layoutManager = LinearLayoutManager(this@HomeActivity)
                    adapter = pemilihAdapter
                }

                showToast("Pemilih data loaded successfully")

            } catch (e: Exception) {
                showToast("Error loading data: ${e.message}")
            }
        }
    }

    private fun deletePemilih(pemilih: Pemilih) {
        lifecycleScope.launch {
            try {
                db.pemilihDao().delete(pemilih)
                loadPemilihData()
                showToast("Pemilih deleted successfully")
            } catch (e: Exception) {
                showToast("Error deleting pemilih: ${e.message}")
            }
        }
    }

    private fun editPemilih(pemilih: Pemilih) {
        val intent = Intent(this, EditActivity::class.java)
        intent.putExtra("PEMILIH_ID", pemilih.id)
        startActivity(intent)
        showToast("Navigating to Edit Pemilih page")
    }

    private fun showDetailPemilih(pemilih: Pemilih) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("PEMILIH_ID", pemilih.id)
        startActivity(intent)
        showToast("Navigating to Pemilih details")
    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
