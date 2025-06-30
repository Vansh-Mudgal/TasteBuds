package com.example.tastebuds.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.tastebuds.HomeViewModel
import com.example.tastebuds.InfoViewModel
import com.example.tastebuds.databinding.HomeRvItemBinding

class HomeAdapter(var data : ArrayList<HomeViewModel>, val infoModel : InfoViewModel, val fragment : Fragment) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(HomeRvItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = data[position]
        val itemPrice = "₹${item.price}"
        holder.binding.foodName.text = item.foodName
        holder.binding.foodPrice.text = itemPrice
        holder.binding.imageView3.setImageResource(item.image)

        holder.binding.root.setOnClickListener{
            infoModel.setData(item)
            val infoBottomSheet = FoodInfoBottomSheetFragment()
            infoBottomSheet.show(fragment.childFragmentManager, "Hello")
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class HomeViewHolder(var binding : HomeRvItemBinding) : RecyclerView.ViewHolder(binding.root){
    }
}