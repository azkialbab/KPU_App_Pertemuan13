package com.example.kpu.database

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pemilih")
data class Pemilih(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val nik: Long,
    val gender: String,
    val alamat: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        id = parcel.readLong(),
        name = parcel.readString() ?: "",
        nik = parcel.readLong(),
        gender = parcel.readString() ?: "",
        alamat = parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeLong(nik)
        parcel.writeString(gender)
        parcel.writeString(alamat)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Pemilih> {
        override fun createFromParcel(parcel: Parcel): Pemilih = Pemilih(parcel)
        override fun newArray(size: Int): Array<Pemilih?> = arrayOfNulls(size)
    }
}
