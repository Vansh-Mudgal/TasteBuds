package com.example.tastebuds.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.tastebuds.HomeViewModel
import com.example.tastebuds.InfoViewModel
import com.example.tastebuds.databinding.HomeRvItemBinding

class MenuBottomSheetAdapter (var menuList : ArrayList<HomeViewModel>, val infoModel : InfoViewModel, val fragment : Fragment) : RecyclerView.Adapter<MenuBottomSheetAdapter.MenuBSViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuBSViewHolder {
        val view = MenuBSViewHolder(HomeRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        return view
    }

    override fun getItemCount(): Int = menuList.size

    override fun onBindViewHolder(holder: MenuBSViewHolder, position: Int) {
        val item = menuList[position]
        val p = "₹${item.price}"
        holder.binding.foodName.text = item.foodName
        holder.binding.foodPrice.text = p
        holder.binding.imageView3.setImageResource(item.image)

        holder.binding.root.setOnClickListener{
            infoModel.setData(item)
            val infoBottomSheet = FoodInfoBottomSheetFragment()
            infoBottomSheet.show(fragment.childFragmentManager, "Hello")
        }
    }

    inner class MenuBSViewHolder (var binding : HomeRvItemBinding) : RecyclerView.ViewHolder(binding.root){

    }
}