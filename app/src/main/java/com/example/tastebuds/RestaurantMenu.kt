package com.example.tastebuds

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tastebuds.adapter.RestaurantMenuAdapter
import com.example.tastebuds.databinding.ActivityRestaurantMenuBinding
import com.google.firebase.firestore.FirebaseFirestore

class RestaurantMenu : AppCompatActivity() {
    private lateinit var binding : ActivityRestaurantMenuBinding
    private lateinit var restaurantMenuAdapter: RestaurantMenuAdapter
    private lateinit var infoModel : InfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantMenuBinding.inflate(layoutInflater)

        val id = intent.getStringExtra("id")
        val rating = intent.getDoubleExtra("rating", 0.0)
        infoModel = ViewModelProvider(this)[InfoViewModel::class.java]
        val db = FirebaseFirestore.getInstance()
        val dishes = ArrayList<CartViewModel>()
        val restaurantFood = db.collection("restaurants").document(id.toString()).collection("menu")

        binding.menuRestaurantName.text = id
        binding.menuRestaurantRating.text = rating.toString()

        restaurantMenuAdapter = RestaurantMenuAdapter(dishes, infoModel, supportFragmentManager)

        restaurantFood.get().addOnSuccessListener { menuItems ->
            dishes.clear()
            for(dish in menuItems){
                dishes.add(CartViewModel(dish.getString("foodname"), dish.getLong("price"), dish.getString("image"), id, dish.id))
            }
            restaurantMenuAdapter.notifyDataSetChanged()
        }

        binding.menuRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.menuRecyclerView.adapter = restaurantMenuAdapter

        setContentView(binding.root)
    }
}