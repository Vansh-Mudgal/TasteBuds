package com.example.tastebuds.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tastebuds.CartViewModel
import com.example.tastebuds.PlaceOrderActivity
import com.example.tastebuds.adapter.CartAdapter
import com.example.tastebuds.databinding.FragmentCartBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore

class CartFragment : Fragment() {
    private lateinit var bindingCart : FragmentCartBinding
    private lateinit var cartList : ArrayList<CartViewModel>
    private lateinit var quantityList : MutableList<Int>
    private lateinit var db : FirebaseFirestore
    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingCart = FragmentCartBinding.inflate(inflater, container, false)
        cartList = ArrayList()
        quantityList = ArrayList()
        bindingCart.emptyCartImage.visibility = View.INVISIBLE
        bindingCart.cartProceedButton.alpha = 0.5f
        return bindingCart.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val auth = Firebase.auth
        db = FirebaseFirestore.getInstance()

        auth.uid?.let { db.collection("UserMenu").document(it).get().addOnSuccessListener { userArray ->
            cartList.clear()
            quantityList.clear()
            val itemValues = userArray.data?.values?.filterIsInstance<Map<*, *>>() ?: emptyList()
            if(!userArray.exists() || itemValues.isEmpty()){
                bindingCart.emptyCartImage.visibility = View.VISIBLE
            }
            var loadedCount = 0
            userArray.data?.forEach{(_, value) ->
                if(value is Map<*,*>) {
                    val foodName = value["foodName"].toString()
                    val restName = value["restaurantName"].toString()
                    val foodID = value["foodID"].toString()
                    val quantity = value["quantity"].toString().toIntOrNull() ?: 1
                    quantityList.add(quantity)
                    db.collection("restaurants").document(restName).collection("menu").document(foodID).get().addOnSuccessListener { myData ->
                        cartList.add(CartViewModel(foodName, myData.getLong("price"), myData.getString("image"), restName, foodID))
                        loadedCount++
                        if (loadedCount == itemValues.size) {
                            if(cartList.isNotEmpty()){
                                bindingCart.cartProceedButton.alpha = 1.0f
                                bindingCart.emptyCartImage.visibility = View.GONE
                                cartAdapter = CartAdapter(cartList, quantityList)
                                bindingCart.recyclerView.layoutManager = LinearLayoutManager(requireContext())
                                bindingCart.recyclerView.adapter = cartAdapter
                                cartAdapter.notifyDataSetChanged()
                            }
                        }
                    }
                }
            }
        }}
        bindingCart.cartProceedButton.setOnClickListener{
            if(bindingCart.cartProceedButton.alpha == 1.0f) {
                val intent = Intent(requireContext(), PlaceOrderActivity::class.java)
                var totalSum = 0
                for(i in 0 until cartList.size){
                    totalSum += (cartList[i].price.toString().toIntOrNull() ?: 0) * quantityList[i]
                }
                intent.putExtra("total", totalSum)
                intent.putParcelableArrayListExtra("foodData", cartList)
                startActivity(intent)
            }
        }
    }

    companion object {

    }
}