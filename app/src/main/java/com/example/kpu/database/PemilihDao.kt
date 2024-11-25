package com.example.kpu.database

import androidx.room.*

@Dao
interface PemilihDao {
    @Insert
    suspend fun insert(pemilih: Pemilih)

    @Update
    suspend fun update(pemilih: Pemilih)

    @Delete
    suspend fun delete(pemilih: Pemilih)

    @Query("SELECT * FROM pemilih")
    suspend fun getAllPemilih(): List<Pemilih>
}
