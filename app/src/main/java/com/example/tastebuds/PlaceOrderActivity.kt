package com.example.tastebuds

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Printer
import android.widget.Toast
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
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import com.shashank.sony.fancytoastlib.FancyToast
import org.json.JSONObject
import java.util.ArrayList

class PlaceOrderActivity : AppCompatActivity(), PaymentResultListener {
    private lateinit var binding : ActivityPlaceOrderBinding
    val db = FirebaseFirestore.getInstance()
    val auth = Firebase.auth
    val realDB = FirebaseDatabase.getInstance()
    var name: String = ""
    var phone: String = ""
    var address: String = ""
    var price = ""
    private lateinit var foodData : ArrayList<CartViewModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPlaceOrderBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //razorpay
        Checkout.preload(applicationContext)
        val co = Checkout()
        co.setKeyID("rzp_test_gD1mlTgDekUgL8")
        //razorpay sub end

        price = intent.getIntExtra("total", 0).toString()
        // getting data based on API level (33 or below)
        foodData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableArrayListExtra("foodData", CartViewModel::class.java)?:arrayListOf()
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableArrayListExtra("foodData")?:arrayListOf()
        }

        binding.totalCostText.text = price
        binding.placeOrderButton.alpha = 0.5f

        binding.placeOrderButton.setOnClickListener{
            if(binding.placeOrderButton.alpha == 1.0f){
                if (foodData.isNotEmpty()) {
                    //razorpay
                    payNow()
                }
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

    private fun payNow() {
        val activity: Activity = this
        val co = Checkout()

        try {
            val options = JSONObject()
            options.put("name","Taste Buds")
            options.put("description","Food Ordering App")
            //You can omit the image option to fetch the image from the Dashboard
            options.put("image","http://example.com/image/rzp.jpg")
            options.put("theme.color", "#15BE77");
            options.put("currency","INR");
            //options.put("order_id", "order_DBJOWzybf0sJbb");
            options.put("amount",price)//pass amount in currency subunits

            val retryObj = JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 2);
            options.put("retry", retryObj);

            val prefill = JSONObject()
            prefill.put("email","gaurav.kumar@example.com")
            prefill.put("contact","+919876543210")

            options.put("prefill",prefill)
            co.open(activity,options)
        }catch (e: Exception){
            Toast.makeText(activity,"Error in payment: "+ e.message,Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    fun check(name : String, phone : String, address : String){
        if(name.isNotBlank() && phone.isNotBlank() && address.isNotBlank() && phone.length == 10){
            binding.placeOrderButton.alpha = 1.0f
        }
        else{
            binding.placeOrderButton.alpha = 0.5f
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        runOnUiThread {
            val placeOrder = CongratulationsBottomSheetFragment()
            for (item in foodData) {
                val items = mapOf(
                    "foodName" to item.foodName,
                    "restaurantName" to item.restName,
                    "image" to item.image,
                    "price" to item.price,
                    "foodID" to item.foodId
                )
                val singleItem = hashMapOf(
                    item.restName + item.foodId to items
                )
                auth.uid?.let { it1 ->
                    db.collection("OrderHistory").document(it1).set(singleItem, SetOptions.merge())
                }
            }
            auth.uid?.let { it1 -> db.collection("UserMenu").document(it1).delete() }
            placeOrder.show(supportFragmentManager, "")
        }
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        FancyToast.makeText(this, "Error : Try Again", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show()
    }
}