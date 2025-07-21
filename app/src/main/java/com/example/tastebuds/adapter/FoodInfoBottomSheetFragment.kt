package com.example.tastebuds.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.tastebuds.InfoViewModel
import com.example.tastebuds.databinding.FragmentFoodInfoBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.shashank.sony.fancytoastlib.FancyToast

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

        val infoModel = ViewModelProvider(requireActivity())[InfoViewModel::class.java]
        infoModel.getData().observe(viewLifecycleOwner,{
            val price = "₹${it.price}"
            binding.infoFoodName.text = it.foodName
            binding.infoFoodPrice.text = price
            Glide.with(requireContext()).load(it.image).into(binding.infoFoodImage)
            createCartItem(it.restName, it.foodName, it.foodId)
        })


        return binding.root
    }

    private fun createCartItem(restaurantName: String?, foodName: String?, foodId : String){
        val db = FirebaseFirestore.getInstance()
        val user = Firebase.auth.currentUser

        val items = mapOf("foodName" to foodName, "restaurantName" to restaurantName, "quantity" to 1, "foodID" to foodId)
        val singleItem = hashMapOf(
            foodName+restaurantName to items
        )
        binding.infoBottomSheetAddToCart.setOnClickListener{
            user?.uid?.let { uid ->
                db.collection("UserMenu").document(uid).set(singleItem, SetOptions.merge())
            }?.addOnFailureListener{
                Toast.makeText(requireContext(), "Error adding item. Please try again", Toast.LENGTH_SHORT).show()
            }?.addOnSuccessListener {
                FancyToast.makeText(requireContext(),"Added",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,true).show()
                dismiss()
            }
        }
    }

    companion object {

    }
}