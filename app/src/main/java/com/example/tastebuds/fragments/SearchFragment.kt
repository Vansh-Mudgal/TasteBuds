package com.example.tastebuds.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tastebuds.HomeViewModel
import com.example.tastebuds.InfoViewModel
import com.example.tastebuds.R
import com.example.tastebuds.RestaurantMenu
import com.example.tastebuds.RestaurantViewModel
import com.example.tastebuds.adapter.HomeAdapter
import com.example.tastebuds.databinding.FragmentSearchBinding
import com.google.firebase.firestore.FirebaseFirestore

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchAdapter : HomeAdapter
    private lateinit var firestoreDB : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private var itemList = ArrayList<RestaurantViewModel>()
    private var restaurantList = ArrayList<RestaurantViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        firestoreDB = FirebaseFirestore.getInstance()
        val infoModel = ViewModelProvider(requireActivity())[InfoViewModel::class.java]

        searchAdapter = HomeAdapter(itemList, this){ selectedItem ->
            val intent = Intent(requireContext(), RestaurantMenu::class.java)
            intent.putExtra("id", selectedItem.restName)
            intent.putExtra("rating", selectedItem.rating)
            startActivity(intent)
        }

        val restaurants = firestoreDB.collection("restaurants")
        restaurants.get().addOnSuccessListener { restaurant ->
            itemList.clear()
            for(single in restaurant){
                itemList.add(RestaurantViewModel(single.id, single.getDouble("rating") ?: 0.0, single.getString("restImage") ?: ""))
            }
            restaurantList = ArrayList(itemList)
            searchAdapter.notifyDataSetChanged()
        }

        binding.searchRecView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.searchRecView.adapter = searchAdapter

        setSearchResults()

        return binding.root
    }

    private fun setSearchResults() {
        binding.searchViewId.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                filterMenuItems(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterMenuItems(newText)
                return true
            }

        })
    }

    private fun filterMenuItems(query: String) {
        itemList.clear()
        restaurantList.forEachIndexed{index, item ->
            if(item.restName.contains(query, ignoreCase = true)){
                itemList.add(item)
            }
        }
        searchAdapter.notifyDataSetChanged()
    }

    companion object {

    }
}


