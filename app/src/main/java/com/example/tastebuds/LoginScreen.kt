package com.example.tastebuds

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tastebuds.databinding.ActivityLoginScreenBinding

class LoginScreen : AppCompatActivity() {
    private val binding : ActivityLoginScreenBinding by lazy {
        ActivityLoginScreenBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.loginToSignUpText.setOnClickListener{
            val intent = Intent(this, SignUpScreen::class.java)
            startActivity(intent)
        }
    }
}