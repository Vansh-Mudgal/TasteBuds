package com.example.tastebuds

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tastebuds.databinding.ActivitySignUpScreenBinding

class SignUpScreen : AppCompatActivity() {
    private val binding : ActivitySignUpScreenBinding by lazy {
        ActivitySignUpScreenBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.createAccountBttn.setOnClickListener{
            val intent = Intent(this, LocationScreen::class.java)
            startActivity(intent)
        }
    }
}