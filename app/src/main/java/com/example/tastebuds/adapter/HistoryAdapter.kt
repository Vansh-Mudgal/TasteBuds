package com.example.tastebuds.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tastebuds.HomeViewModel
import com.example.tastebuds.databinding.HistoryRvItemBinding

class HistoryAdapter(val historyData : ArrayList<HomeViewModel>, var restaurants : ArrayList<String>) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>(){

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
        holder.binding.historyFoodImage.setImageResource(item.image)
        holder.binding.historyRestaurant.text = restaurants[position]
    }

    inner class HistoryViewHolder(var binding : HistoryRvItemBinding) : RecyclerView.ViewHolder(binding.root){

    }
}