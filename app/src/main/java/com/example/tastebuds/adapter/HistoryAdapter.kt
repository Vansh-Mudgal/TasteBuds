package com.example.tastebuds.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tastebuds.HistoryViewModel
import com.example.tastebuds.databinding.HistoryRvItemBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class HistoryAdapter(private val historyData: ArrayList<HistoryViewModel>, private val context: Context) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>(){

    val auth = Firebase.auth
    val db = FirebaseFirestore.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = HistoryViewHolder(HistoryRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        return view
    }

    override fun getItemCount(): Int = historyData.size

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = historyData[position]
        val cost = "₹${item.price}"
        holder.binding.historyFoodname.text = item.foodName
        holder.binding.historyPrice.text = cost
        Glide.with(holder.binding.root).load(item.image).into(holder.binding.historyFoodImage)
        holder.binding.historyRestaurant.text = item.restName

        holder.binding.buyAgain.setOnClickListener{
            val items = mapOf("foodName" to item.foodName, "restaurantName" to item.restName, "quantity" to 1, "foodID" to item.foodId)
            val singleItem = hashMapOf(
                item.foodName+item.restName to items
            )
            auth.uid?.let { it1 -> db.collection("UserMenu").document(it1).set(singleItem, SetOptions.merge()) }
                ?.addOnFailureListener {
                    Toast.makeText(context, "Error adding item. Please try again", Toast.LENGTH_SHORT).show()
                }
        }
    }

    inner class HistoryViewHolder(var binding : HistoryRvItemBinding) : RecyclerView.ViewHolder(binding.root){

    }
}