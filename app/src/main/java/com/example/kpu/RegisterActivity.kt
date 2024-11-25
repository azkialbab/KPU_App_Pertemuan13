package com.example.kpu

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.method.HideReturnsTransformationMethod
import android.text.method.LinkMovementMethod
import android.text.method.PasswordTransformationMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.kpu.database.AppDatabase
import com.example.kpu.database.Users
import com.example.kpu.databinding.ActivityRegisterBinding
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var db: AppDatabase
    private lateinit var preferenceHelper: PrefManager
    private var isPasswordVisible: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "app_db")
            .fallbackToDestructiveMigration()
            .build()
        preferenceHelper = PrefManager(this)


        binding.btnRegister.setOnClickListener {
            registerUser()
        }

        binding.tvlogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }


        setupPasswordToggle()


        setLoginText()
    }

    private fun registerUser() {
        val username = binding.rusername.text.toString()
        val email = binding.remail.text.toString()
        val phone = binding.rphone.text.toString()
        val password = binding.rpassword.text.toString()


        if (username.isBlank() || email.isBlank() || phone.isBlank() || password.isBlank()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val user = Users(username = username, email = email, phone = phone, password = password)


        lifecycleScope.launch {
            db.userDao().insert(user)
            preferenceHelper.saveUser(username)
            startActivity(Intent(this@RegisterActivity, HomeActivity::class.java))
            finish()
        }
    }

    private fun setupPasswordToggle() {
        binding.rpassword.setOnTouchListener { v, event ->
            val drawableEnd = 2
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (binding.rpassword.right - binding.rpassword.compoundDrawables[drawableEnd].bounds.width())) {
                    togglePasswordVisibility()
                    return@setOnTouchListener true
                }
            }
            false
        }
    }

    private fun togglePasswordVisibility() {
        if (isPasswordVisible) {
            binding.rpassword.transformationMethod = PasswordTransformationMethod.getInstance()
            binding.rpassword.setCompoundDrawablesRelativeWithIntrinsicBounds(
                R.drawable.lock, 0, R.drawable.eye_icon_close, 0
            )
        } else {
            binding.rpassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            binding.rpassword.setCompoundDrawablesRelativeWithIntrinsicBounds(
                R.drawable.lock, 0, R.drawable.eye_open, 0
            )
        }

        binding.rpassword.setSelection(binding.rpassword.text.length)
        isPasswordVisible = !isPasswordVisible
    }

    private fun setLoginText() {
        val text = "Already Have an Account? Log In"
        val spannableString = SpannableString(text)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }

        spannableString.setSpan(
            ForegroundColorSpan(Color.BLUE),
            25, 31,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannableString.setSpan(
            clickableSpan,
            25, 31,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        with(binding) {
            tvlogin.text = spannableString
            tvlogin.movementMethod = LinkMovementMethod.getInstance()
        }
    }
}
