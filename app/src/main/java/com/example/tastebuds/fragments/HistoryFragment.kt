package com.example.tastebuds.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tastebuds.HistoryViewModel
import com.example.tastebuds.adapter.HistoryAdapter
import com.example.tastebuds.databinding.FragmentHistoryBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore

class HistoryFragment : Fragment() {

    private lateinit var binding : FragmentHistoryBinding
    private lateinit var db : FirebaseFirestore
    private lateinit var auth : FirebaseAuth
    private lateinit var historyList : ArrayList<HistoryViewModel>
    private lateinit var historyAdapter : HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)

        binding.emptyBox.visibility = View.INVISIBLE
        binding.noOrdersText.visibility = View.INVISIBLE

        auth = Firebase.auth
        db = FirebaseFirestore.getInstance()
        historyList = ArrayList()

        auth.uid?.let { db.collection("OrderHistory").document(it).get().addOnSuccessListener { userArray ->
            historyList.clear()
            val itemValues = userArray.data?.values?.filterIsInstance<Map<*, *>>() ?: emptyList()
            if(!userArray.exists() || itemValues.isEmpty()){
                binding.emptyBox.visibility = View.VISIBLE
                binding.noOrdersText.visibility = View.VISIBLE
            }
            var loadedCount = 0
            userArray.data?.forEach{ (_, value) ->
                if(value is Map<*,*>){
                    val foodName = value["foodName"].toString()
                    val restName = value["restaurantName"].toString()
                    val image = value["image"].toString()
                    val price = value["price"].toString()
                    val foodId = value["foodID"].toString()
                    historyList.add(HistoryViewModel(foodName, price, image, restName, foodId))
                    loadedCount++
                    if(loadedCount == itemValues.size){
                        if(historyList.isNotEmpty()){
                            binding.emptyBox.visibility = View.GONE
                            binding.noOrdersText.visibility = View.GONE
                            historyAdapter = HistoryAdapter(historyList, requireContext())
                            binding.historyRecView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                            binding.historyRecView.adapter = historyAdapter
                        }
                    }
                }
            }
        }}

        return binding.root
    }

    companion object {

    }
}