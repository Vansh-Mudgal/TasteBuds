package com.example.tastebuds.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tastebuds.CartViewModel
import com.example.tastebuds.InfoViewModel
import com.example.tastebuds.databinding.MenurvitemBinding

class RestaurantMenuAdapter(private val dishes : ArrayList<CartViewModel>, private val infoModel : InfoViewModel, private val fragmentManager: FragmentManager): RecyclerView.Adapter<RestaurantMenuAdapter.RestaurantMenuViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantMenuViewHolder {
        val view = RestaurantMenuViewHolder(MenurvitemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        return view
    }

    override fun getItemCount(): Int = dishes.size

    override fun onBindViewHolder(holder: RestaurantMenuViewHolder, position: Int) {
        val item = dishes[position]
        val price = "₹${item.price.toString()}"
        holder.binding.menuFoodName.text = item.foodName
        holder.binding.textView9.text = price
        Glide.with(holder.binding.root).load(item.image).into(holder.binding.menuFoodImage)

        holder.binding.root.setOnClickListener{
            infoModel.setData(item)
            val infoBottomSheet = FoodInfoBottomSheetFragment()
            infoBottomSheet.show(fragmentManager, "Hello")
        }
    }

    inner class RestaurantMenuViewHolder(val binding : MenurvitemBinding) : RecyclerView.ViewHolder(binding.root){

    }
}