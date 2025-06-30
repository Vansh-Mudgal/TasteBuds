package com.example.tastebuds.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tastebuds.FoodMenu
import com.example.tastebuds.InfoViewModel
import com.example.tastebuds.R
import com.example.tastebuds.adapter.MenuBottomSheetAdapter
import com.example.tastebuds.databinding.FragmentMenuBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class MenuBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentMenuBottomSheetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMenuBottomSheetBinding.inflate(inflater, container, false)
        val infoModel = ViewModelProvider(requireActivity()).get(InfoViewModel::class.java)
        binding.backButton.setOnClickListener{
            dismiss()
        }
        val bottomSheetAdapter = MenuBottomSheetAdapter(FoodMenu.getData(),infoModel,this)
        binding.bttmSheetRecView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.bttmSheetRecView.adapter = bottomSheetAdapter

        return binding.root
    }


    companion object {

    }
}