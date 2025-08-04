package com.example.tastebuds

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.collection.emptyLongSet
import com.example.tastebuds.databinding.ActivitySignUpScreenBinding
import com.example.tastebuds.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class SignUpScreen : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var userName : String
    private lateinit var password : String
    private lateinit var email : String
    private lateinit var database : DatabaseReference
    private lateinit var launcher: ActivityResultLauncher<Intent>


    private val binding : ActivitySignUpScreenBinding by lazy {
        ActivitySignUpScreenBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //initializing variables
        auth = Firebase.auth
        database = Firebase.database.reference
        //sign in with google start
        val scope = CoroutineScope(Dispatchers.Main)
        val login : (String, String) -> Unit = { username, email ->
            val user = UserModel(username, email, "", "", "", "", "")
            auth.uid?.let { database.child("user").child(it).setValue(user)}
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
        }

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            GoogleSignInUtils.doGoogleSignIn(
                this,
                scope,
                null,
                login
            )
        }

        binding.signUpGoogle.setOnClickListener{
            GoogleSignInUtils.doGoogleSignIn(
                this,
                scope,
                null,
                login
            )
        }
        // sign in with google end
        binding.createAccountBttn.setOnClickListener{
            userName = binding.signUpName.text.toString().trim()
            password = binding.signUpPassword.text.toString().trim()
            email = binding.signUpEmail.text.toString().trim()

            if(userName.isBlank() || password.isBlank() || email.isBlank()){
                Toast.makeText(this, "Please Fill all the Fields", Toast.LENGTH_SHORT).show()
            }
            else{
                createUser(email, password)
            }
        }
    }

    private fun createUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{ work ->
            if(work.isSuccessful) {
                Toast.makeText(this, "User Created Successfully", Toast.LENGTH_SHORT).show()
                saveUserData()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this, "Error : User not created", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveUserData() {
        val user = UserModel(userName, email, password, "", "", "", "")
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        database.child("user").child(userId).setValue(user)
    }
}