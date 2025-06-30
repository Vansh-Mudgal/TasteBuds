package com.example.tastebuds

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tastebuds.databinding.ActivityPlaceOrderBinding
import com.example.tastebuds.fragments.CongratulationsBottomSheetFragment

class PlaceOrderActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPlaceOrderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPlaceOrderBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.placeOrderButton.setOnClickListener{
            val placeOrder = CongratulationsBottomSheetFragment()
            placeOrder.show(supportFragmentManager, "")
        }
        binding.cancelButton.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}