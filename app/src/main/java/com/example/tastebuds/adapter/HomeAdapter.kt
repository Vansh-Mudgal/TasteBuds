package com.example.tastebuds.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tastebuds.InfoViewModel
import com.example.tastebuds.RestaurantMenu
import com.example.tastebuds.RestaurantViewModel
import com.example.tastebuds.databinding.HomeRvItemBinding

class HomeAdapter(private var data : ArrayList<RestaurantViewModel>, val fragment : Fragment, private val onItemClick : (RestaurantViewModel) -> Unit) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(HomeRvItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = data[position]
        holder.binding.restaurantName.text = item.restName
        Glide.with(holder.binding.root).load(item.restImage).into(holder.binding.restaurantMainImage)
        holder.binding.rating.text = item.rating.toString()

        holder.binding.root.setOnClickListener{
            onItemClick(item)
        }
//        holder.binding.root.setOnClickListener{
//            infoModel.setData(item)
//            val infoBottomSheet = FoodInfoBottomSheetFragment()
//            infoBottomSheet.show(fragment.childFragmentManager, "Hello")
//       }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class HomeViewHolder(var binding : HomeRvItemBinding) : RecyclerView.ViewHolder(binding.root){
    }
}