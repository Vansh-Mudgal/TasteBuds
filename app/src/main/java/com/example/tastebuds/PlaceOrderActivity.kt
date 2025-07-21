package com.example.tastebuds

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Printer
import androidx.appcompat.app.AppCompatActivity
import com.example.tastebuds.databinding.ActivityPlaceOrderBinding
import com.example.tastebuds.fragments.CongratulationsBottomSheetFragment
import com.example.tastebuds.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class PlaceOrderActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPlaceOrderBinding
    var name: String = ""
    var phone: String = ""
    var address: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPlaceOrderBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val db = FirebaseFirestore.getInstance()
        val auth = Firebase.auth
        val realDB = FirebaseDatabase.getInstance()

        val price = intent.getIntExtra("total", 0)
        // getting data based on API level (33 or below)
        val foodData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableArrayListExtra("foodData", CartViewModel::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableArrayListExtra("foodData")
        }

        binding.totalCostText.text = price.toString()
        binding.placeOrderButton.alpha = 0.5f

        binding.placeOrderButton.setOnClickListener{
            if(binding.placeOrderButton.alpha == 1.0f){
                val placeOrder = CongratulationsBottomSheetFragment()
                if (foodData != null) {
                    for(item in foodData) {
                        val items = mapOf("foodName" to item.foodName, "restaurantName" to item.restName,"image" to item.image, "price" to item.price, "foodID" to item.foodId)
                        val singleItem = hashMapOf(
                            item.restName+item.foodId to items
                        )
                        auth.uid?.let { it1 -> db.collection("OrderHistory").document(it1).set(singleItem, SetOptions.merge()) }
                    }
                }
                auth.uid?.let { it1 -> db.collection("UserMenu").document(it1).delete() }
                placeOrder.show(supportFragmentManager, "")
            }
        }
        binding.cancelButton.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        // setting edit texts
        auth.uid?.let { realDB.getReference("user").child(it).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val userData = snapshot.getValue(UserModel::class.java)
                binding.editTextName.setText(userData?.userName)
                name = userData?.userName.toString()
                binding.editTextPhone.setText(userData?.phone)
                phone = userData?.phone.toString()
                binding.editTextAddress.setText(userData?.address)
                address = userData?.address.toString()
                check(userData?.userName.toString(), userData?.phone.toString(), userData?.address.toString())
            }


            override fun onCancelled(error: DatabaseError) {}
        }) }

        binding.editTextName.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                name = s.toString()
                check(name, phone, address)
            }

        })
        binding.editTextPhone.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                phone = s.toString()
                check(name, phone, address)
            }

        })
        binding.editTextAddress.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                address = s.toString()
                check(name, phone, address)
            }

        })
    }
    fun check(name : String, phone : String, address : String){
        if(name.isNotBlank() && phone.isNotBlank() && address.isNotBlank() && phone.length == 10){
            binding.placeOrderButton.alpha = 1.0f
        }
        else{
            binding.placeOrderButton.alpha = 0.5f
        }
    }
}