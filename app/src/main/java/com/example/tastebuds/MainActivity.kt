package com.example.tastebuds

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.tastebuds.databinding.ActivityMainBinding
import com.example.tastebuds.fragments.NotificationFragment

class MainActivity : AppCompatActivity() {
    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var nav_Controller = findNavController(R.id.fragmentContainer)
        binding.bottomNavigationView.setupWithNavController(nav_Controller)

        binding.bellIcon.setOnClickListener{
            val notificate = NotificationFragment()
            notificate.show(supportFragmentManager,"Hell")
        }
    }
}