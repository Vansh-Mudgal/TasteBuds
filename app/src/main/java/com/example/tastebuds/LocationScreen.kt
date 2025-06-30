package com.example.tastebuds

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.helper.widget.Carousel.Adapter
import com.example.tastebuds.databinding.ActivityLocationScreenBinding

class LocationScreen : AppCompatActivity() {
    private val binding : ActivityLocationScreenBinding by lazy {
        ActivityLocationScreenBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val locationList = arrayOf("Hapur","Ghaziabad","Delhi","Meerut","Muzaffarnagar","Kannauj","Lucknow")
        val adaptor = ArrayAdapter(this,android.R.layout.simple_list_item_1, locationList)
        binding.locationDrop.setAdapter(adaptor)
        binding.doneButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent);
        }
    }
}