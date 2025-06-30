package com.example.tastebuds.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.tastebuds.InfoViewModel
import com.example.tastebuds.databinding.FragmentFoodInfoBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FoodInfoBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentFoodInfoBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFoodInfoBottomSheetBinding.inflate(inflater, container, false)

        val infoModel = ViewModelProvider(requireActivity()).get(InfoViewModel::class.java)
        infoModel.getData().observe(viewLifecycleOwner,{
            val price = "₹${it.price}"
            binding.infoFoodName.text = it.foodName
            binding.infoFoodPrice.text = price
            binding.infoFoodImage.setImageResource(it.image)
        })
        return binding.root
    }

    companion object {

    }
}