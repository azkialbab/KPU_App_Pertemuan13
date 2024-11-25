package com.example.kpu

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity


class PrefManager(context: Context) {
    private val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun saveUser (username: String) {
        prefs.edit().putString("username", username).apply()
    }

    fun getUser (): String? {
        return prefs.getString("username", null)
    }

    fun clear() {
        prefs.edit().clear().apply()
    }
}