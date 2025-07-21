package com.example.tastebuds.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tastebuds.R
import com.example.tastebuds.databinding.FragmentProfileBinding
import com.example.tastebuds.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.shashank.sony.fancytoastlib.FancyToast


class ProfileFragment : Fragment() {

    private lateinit var binding : FragmentProfileBinding
    private lateinit var un : String
    private lateinit var up : String
    private lateinit var ua : String
    private lateinit var name : String
    private lateinit var phone : String
    private lateinit var address : String
    private val db = FirebaseDatabase.getInstance()
    private val user = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        user.uid?.let { db.getReference("user").child(it).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val userData = snapshot.getValue(UserModel::class.java)
                binding.userName.setText(userData?.userName)
                name = userData?.userName.toString()
                binding.userPhone.setText(userData?.phone)
                phone = userData?.phone.toString()
                binding.userAddress.setText(userData?.address)
                address = userData?.address.toString()
                binding.userEmail.text = userData?.email
            }

            override fun onCancelled(error: DatabaseError) {}

        }) }

        name = binding.userName.text.toString()
        phone = binding.userPhone.text.toString()
        address = binding.userAddress.text.toString()
        un = name
        up = phone
        ua = address

        binding.proceedButton.alpha = 0.5f

        binding.userName.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                un = s.toString()
                check()
            }

        })
        binding.userPhone.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                up = s.toString()
                check()
            }

        })
        binding.userAddress.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                ua = s.toString()
                check()
            }

        })



        binding.proceedButton.setOnClickListener {
            if(binding.proceedButton.alpha == 1.0f){
                name = un
                phone = up
                address = ua
                val items = mapOf<String, Any>(
                    "userName" to un,
                    "phone" to up,
                    "address" to ua
                )
                user.uid?.let { it1 -> db.getReference("user").child(it1).updateChildren(items).addOnSuccessListener {
                    binding.proceedButton.alpha = 0.5f
                    FancyToast.makeText(requireContext(),"Updated",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,true).show()
                } }
            }
        }

        return binding.root
    }

    private fun check() {
        if((name != un || phone != up || address != ua) && (un.isNotBlank() && up.isNotBlank() && ua.isNotBlank()) && up.length == 10){
            binding.proceedButton.alpha = 1.0f
        }
        else{
            binding.proceedButton.alpha = 0.5f
        }
    }

    companion object {

    }
}