package com.example.tastebuds.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tastebuds.MainActivity
import com.example.tastebuds.R
import com.example.tastebuds.databinding.FragmentCongratulationsBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CongratulationsBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentCongratulationsBottomSheetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCongratulationsBottomSheetBinding.inflate(inflater, container, false)

        binding.goHomeButton.setOnClickListener{
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    companion object {

    }
}