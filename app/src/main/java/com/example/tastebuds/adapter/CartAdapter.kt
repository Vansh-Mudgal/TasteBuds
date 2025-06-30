package com.example.tastebuds.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tastebuds.HomeViewModel
import com.example.tastebuds.databinding.CartRvItemBinding

class CartAdapter(var cartList : MutableList<HomeViewModel>) : RecyclerView.Adapter<CartAdapter.CartViewHolder>(){

    var itemQuantities = List(cartList.size){1}.toMutableList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(CartRvItemBinding.inflate(LayoutInflater.from(parent.context), parent,false))
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val listItem = cartList[position]
        val itemPrice = "₹${listItem.price}"
        holder.binding.foodInCartName.text = listItem.foodName
        holder.binding.priceInCart.text = itemPrice
        holder.binding.imageView7.setImageResource(listItem.image)
        holder.binding.itemQuantity.text = itemQuantities[position].toString()
        holder.bind(position)
    }

    inner class CartViewHolder(var binding : CartRvItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int) {
            binding.cartPlus.setOnClickListener{
                increase(position)
            }
            binding.cartMinus.setOnClickListener{
                decrease(position)
            }
            binding.cartDelete.setOnClickListener{
                val itemPosition = adapterPosition
                if(itemPosition != RecyclerView.NO_POSITION)
                    deleteItem(itemPosition)
            }
        }
        private fun increase(position : Int){
            if(itemQuantities[position] < 10){
                itemQuantities[position]++
                binding.itemQuantity.text = itemQuantities[position].toString()
            }
        }
        private fun decrease(position : Int){
            if(itemQuantities[position] > 1){
                itemQuantities[position]--
                binding.itemQuantity.text = itemQuantities[position].toString()
            }
        }
        private fun deleteItem(position : Int){
            cartList.removeAt(position)
            itemQuantities.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, cartList.size)
        }
    }
}