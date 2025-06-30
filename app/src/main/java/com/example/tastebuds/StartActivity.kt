package com.example.tastebuds

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.example.tastebuds.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {
    private val binding : ActivityStartBinding by lazy {
        ActivityStartBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.nextButton.setOnClickListener{
            val intent = Intent(this, LoginScreen::class.java)
            startActivity(intent)
        }
    }
}