package com.example.tastebuds.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.tastebuds.FoodMenu
import com.example.tastebuds.HomeViewModel
import com.example.tastebuds.InfoViewModel
import com.example.tastebuds.R
import com.example.tastebuds.adapter.HomeAdapter
import com.example.tastebuds.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding
    private lateinit var infoModel : InfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)

        binding.viewMenu.setOnClickListener{
            val menuBottomSheet = MenuBottomSheetFragment()
            menuBottomSheet.show(parentFragmentManager, "")
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //for image slider
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.foodbanner1, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.foodbanner2, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.foodbanner3, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.foodbanner4, ScaleTypes.FIT))

        binding.imageSlider.setImageList(imageList, ScaleTypes.FIT)

        infoModel = ViewModelProvider(requireActivity()).get(InfoViewModel::class.java)

        val homeAdapter = HomeAdapter(FoodMenu.getData(), infoModel, this)
        binding.recview.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recview.adapter = homeAdapter
    }

    companion object {

    }
}