package com.example.tastebuds

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tastebuds.databinding.ActivityEditProfileBinding
import com.example.tastebuds.databinding.FragmentProfileBinding
import com.example.tastebuds.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.shashank.sony.fancytoastlib.FancyToast

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding : ActivityEditProfileBinding
    private lateinit var un : String
    private lateinit var up : String
    private lateinit var ua : String
    private lateinit var name : String
    private lateinit var phone : String
    private lateinit var address : String
    private lateinit var udob : String
    private lateinit var ugender : String
    private var gender = ""
    private var dob = ""
    private val db = FirebaseDatabase.getInstance()
    private val user = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)

        val listItems = listOf("Gender","Male","Female","Other")
        val genderAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listItems)
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = genderAdapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                ugender = parent?.getItemAtPosition(position).toString()
                check()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }

        user.uid?.let { db.getReference("user").child(it).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userData = snapshot.getValue(UserModel::class.java)
                binding.editName.setText(userData?.userName)
                name = userData?.userName.toString()
                binding.editPhone.setText(userData?.phone)
                phone = userData?.phone.toString()
                binding.editAddress.setText(userData?.address)
                address = userData?.address.toString()
                binding.editDOB.setText(userData?.dob)
                dob = userData?.dob.toString()
                gender = userData?.gender.toString()
                if(gender != "") {
                    val pos = genderAdapter.getPosition(userData?.gender.toString())
                    binding.spinner.setSelection(pos)
                }
            }

            override fun onCancelled(error: DatabaseError) {}

        }) }

        name = binding.editName.text.toString()
        phone = binding.editPhone.text.toString()
        address = binding.editAddress.text.toString()
        un = name
        up = phone
        ua = address
        udob = dob
        ugender = gender

        binding.updateButton.alpha = 0.5f

        binding.editName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                un = s.toString()
                check()
            }

        })
        binding.editPhone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                up = s.toString()
                check()
            }

        })
        binding.editAddress.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                ua = s.toString()
                check()
            }

        })
        binding.editDOB.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                udob = s.toString()
                check()
            }

        })



        binding.updateButton.setOnClickListener {
            if(binding.updateButton.alpha == 1.0f){
                name = un
                phone = up
                address = ua
                dob = udob
                gender = ugender
                val items = mapOf<String, Any>(
                    "userName" to un,
                    "phone" to up,
                    "address" to ua,
                    "dob" to udob,
                    "gender" to ugender
                )
                user.uid?.let { it1 -> db.getReference("user").child(it1).updateChildren(items).addOnSuccessListener {
                    binding.updateButton.alpha = 0.5f
                    FancyToast.makeText(this,"Updated",
                        FancyToast.LENGTH_SHORT,
                        FancyToast.SUCCESS,true).show()
                    startActivity(Intent(this, MainActivity::class.java))
                } }
            }
        }

        setContentView(binding.root)
    }
    private fun check() {
        if((name != un || phone != up || address != ua || dob != udob || gender!= ugender) && (un.isNotBlank() && up.isNotBlank() && ua.isNotBlank()) && up.length == 10){
            binding.updateButton.alpha = 1.0f
        }
        else{
            binding.updateButton.alpha = 0.5f
        }
    }
}