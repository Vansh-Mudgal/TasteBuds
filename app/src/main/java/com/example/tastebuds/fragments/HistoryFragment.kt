package com.example.tastebuds.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tastebuds.FoodMenu
import com.example.tastebuds.R
import com.example.tastebuds.adapter.HistoryAdapter
import com.example.tastebuds.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {

    private lateinit var binding : FragmentHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)

        val historyAdapter = HistoryAdapter(FoodMenu.getData(), FoodMenu.getRestaurants())
        binding.historyRecView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.historyRecView.adapter = historyAdapter

        return binding.root
    }

    companion object {

    }
}