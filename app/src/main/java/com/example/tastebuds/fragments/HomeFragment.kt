package com.example.tastebuds.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.tastebuds.InfoViewModel
import com.example.tastebuds.R
import com.example.tastebuds.RestaurantMenu
import com.example.tastebuds.RestaurantViewModel
import com.example.tastebuds.adapter.HomeAdapter
import com.example.tastebuds.databinding.FragmentHomeBinding
import com.google.firebase.firestore.FirebaseFirestore
import org.json.JSONObject

class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding
    private lateinit var infoModel : InfoViewModel
    private lateinit var firestoreDB : FirebaseFirestore
    private lateinit var restaurantList : ArrayList<RestaurantViewModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        firestoreDB = FirebaseFirestore.getInstance()

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

        infoModel = ViewModelProvider(requireActivity())[InfoViewModel::class.java]

        restaurantList = ArrayList()

        val homeAdapter = HomeAdapter(restaurantList,this) { selectedItem ->
            val intent = Intent(requireContext(), RestaurantMenu::class.java)
            intent.putExtra("id", selectedItem.restName)
            intent.putExtra("rating", selectedItem.rating)
            startActivity(intent)
        }


        val restaurants = firestoreDB.collection("restaurants")
        restaurants.get().addOnSuccessListener { restaurant ->
            restaurantList.clear()
            for(single in restaurant){
                restaurantList.add(RestaurantViewModel(single.id, single.getDouble("rating"), single.getString("restImage") ?: ""))
            }
            homeAdapter.notifyDataSetChanged()
        }

        binding.recview.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recview.adapter = homeAdapter

//        binding.viewMenu.setOnClickListener{
//            // creating data in firestore
//            val jsonString = requireContext().assets.open("restaurantsdata.txt").bufferedReader().use{it.readText()}
//            val jsonObject = JSONObject(jsonString)
//            val restaurants = jsonObject.getJSONObject("restaurants")
//            val restaurantsNames = restaurants.keys()
//            while(restaurantsNames.hasNext()){
//                val restName = restaurantsNames.next()
//                val restData = restaurants.getJSONObject(restName)
//
//                val restImage = restData.getString("restImage")
//                val rating = restData.getDouble("rating")
//                val menu = restData.getJSONObject("menu")
//
//                // Prepare restaurant-level document
//                val restaurantDoc = mapOf(
//                    "restImage" to restImage,
//                    "rating" to rating
//                )
//                // Upload restaurant document
//                firestoreDB.collection("restaurants").document(restName).set(restaurantDoc).addOnSuccessListener {
//                    Toast.makeText(requireContext(),"Restaurants added", Toast.LENGTH_SHORT).show()
//                }
//                // Upload menu as sub-collection
//                val menuKeys = menu.keys()
//                while(menuKeys.hasNext()){
//                    val itemId = menuKeys.next()
//                    val item = menu.getJSONObject(itemId)
//
//                    val foodData = mapOf(
//                        "foodname" to item.getString("foodname"),
//                        "price" to item.getInt("price"),
//                        "image" to item.getString("image")
//                    )
//                    firestoreDB.collection("restaurants").document(restName).collection("menu").document(itemId).set(foodData)
//                }
//            }
//        }
    }
    fun nextActivity(id : String){
        val intent = Intent()
    }

    companion object {

    }
}