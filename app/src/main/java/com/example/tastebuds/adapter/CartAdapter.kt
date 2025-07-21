package com.example.tastebuds.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tastebuds.CartViewModel
import com.example.tastebuds.HomeViewModel
import com.example.tastebuds.databinding.CartRvItemBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class CartAdapter(var cartList : MutableList<CartViewModel>, private var quantityList : MutableList<Int>) : RecyclerView.Adapter<CartAdapter.CartViewHolder>(){

    val db = FirebaseFirestore.getInstance()

    //var itemQuantities = quantityList.toMutableList()
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
        Glide.with(holder.binding.root).load(listItem.image).into(holder.binding.imageView7)
        holder.binding.itemQuantity.text = quantityList[position].toString()
        holder.binding.cartRestName.text = listItem.restName
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
            if(quantityList[position] < 10){
                quantityList[position]++
                val itemData = cartList[position]
                binding.itemQuantity.text = quantityList[position].toString()
                Firebase.auth.uid?.let { db.collection("UserMenu").document(it).update(mapOf("${itemData.foodName}"+"${itemData.restName}.quantity" to quantityList[position])) }
            }
        }
        private fun decrease(position : Int){
            if(quantityList[position] > 1){
                quantityList[position]--
                val itemData = cartList[position]
                binding.itemQuantity.text = quantityList[position].toString()
                Firebase.auth.uid?.let { db.collection("UserMenu").document(it).update(mapOf("${itemData.foodName}"+"${itemData.restName}.quantity" to quantityList[position])) }
            }
        }
        private fun deleteItem(position : Int){
            val itemData = cartList[position]
            val updates = mapOf(itemData.foodName+itemData.restName to FieldValue.delete())
            cartList.removeAt(position)
            quantityList.removeAt(position)
            Firebase.auth.uid?.let { db.collection("UserMenu").document(it).update(updates) }
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, cartList.size)
        }
    }
}