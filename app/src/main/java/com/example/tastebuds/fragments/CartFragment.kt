package com.example.tastebuds.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tastebuds.FoodMenu
import com.example.tastebuds.PlaceOrderActivity
import com.example.tastebuds.adapter.CartAdapter
import com.example.tastebuds.databinding.FragmentCartBinding

class CartFragment : Fragment() {
    private lateinit var bindingCart : FragmentCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingCart = FragmentCartBinding.inflate(inflater, container, false)
        return bindingCart.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cartAdapter = CartAdapter(FoodMenu.getData())
        bindingCart.recyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL, false)
        bindingCart.recyclerView.adapter = cartAdapter
        bindingCart.cartProceedButton.setOnClickListener{
            val intent = Intent(requireContext(), PlaceOrderActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {

    }
}