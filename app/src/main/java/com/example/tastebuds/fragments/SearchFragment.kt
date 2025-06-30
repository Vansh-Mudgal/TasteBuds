package com.example.tastebuds.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tastebuds.FoodMenu
import com.example.tastebuds.HomeViewModel
import com.example.tastebuds.InfoViewModel
import com.example.tastebuds.R
import com.example.tastebuds.adapter.MenuBottomSheetAdapter
import com.example.tastebuds.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchAdapter : MenuBottomSheetAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private var itemList = ArrayList<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        val infoModel = ViewModelProvider(requireActivity()).get(InfoViewModel::class.java)

        itemList.addAll(FoodMenu.getData())
        searchAdapter = MenuBottomSheetAdapter(itemList, infoModel, this)
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
        FoodMenu.getData().forEachIndexed{index, item ->
            if(item.foodName.contains(query, ignoreCase = true)){
                itemList.add(item)
            }
        }
        searchAdapter.notifyDataSetChanged()
    }

    companion object {

    }
}


