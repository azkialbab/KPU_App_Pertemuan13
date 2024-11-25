package com.example.kpu

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.kpu.database.AppDatabase
import com.example.kpu.databinding.ActivityLoginBinding
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var db: AppDatabase
    private lateinit var preferenceHelper: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "app_db")
            .fallbackToDestructiveMigration()
            .build()
        preferenceHelper = PrefManager(this)


        binding.btnLogin.setOnClickListener {
            loginUser()
        }


        setupRegisterText()
    }

    private fun loginUser() {
        val username = binding.lusername.text.toString()
        val password = binding.lpassword.text.toString()


        if (username.isBlank() || password.isBlank()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }


        lifecycleScope.launch {
            val user = db.userDao().login(username, password)
            if (user != null) {
                preferenceHelper.saveUser(username)
                startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                finish()
            } else {
                Toast.makeText(this@LoginActivity, "Login failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRegisterText() {
        val text = "New Member? Register"
        val spannableString = SpannableString(text)

        val registerSpan = object : ClickableSpan() {
            override fun onClick(widget: android.view.View) {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
            }
        }


        spannableString.setSpan(
            ForegroundColorSpan(Color.BLUE),
            text.indexOf("Register"),
            text.indexOf("Register") + "Register".length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannableString.setSpan(
            registerSpan,
            text.indexOf("Register"),
            text.indexOf("Register") + "Register".length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )


        with(binding) {
            tvRegister.text = spannableString
            tvRegister.movementMethod = LinkMovementMethod.getInstance()
        }
    }
}
