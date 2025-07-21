package com.example.tastebuds

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.tastebuds.databinding.ActivityLoginScreenBinding
import com.example.tastebuds.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class LoginScreen : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var password: String
    private lateinit var email: String
    private lateinit var database: DatabaseReference
    private lateinit var launcher: ActivityResultLauncher<Intent>

    private val binding: ActivityLoginScreenBinding by lazy {
        ActivityLoginScreenBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = Firebase.auth
        database = Firebase.database.reference
        //log in with google start
        val scope = CoroutineScope(Dispatchers.Main)
        val login : (String, String) -> Unit = { username, email ->
            val user = UserModel(username, email, "", "", "")
            auth.uid?.let { database.child("user").child(it).setValue(user) }
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
        }

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            GoogleSignInUtils.doGoogleSignIn(
                this,
                scope,
                null,
                login
            )
        }
        binding.loginGoogle.setOnClickListener {
            GoogleSignInUtils.doGoogleSignIn(
                this,
                scope,
                null,
                login
            )
        }
        // log in with google end

        binding.loginButton.setOnClickListener {
            email = binding.loginEmail.text.toString().trim()
            password = binding.loginPassword.text.toString().trim()
            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please Fill All The Details", Toast.LENGTH_SHORT).show()
            } else {
                loginUser(email, password)
            }
        }
        binding.loginToSignUpText.setOnClickListener {
            val intent = Intent(this, SignUpScreen::class.java)
            startActivity(intent)
        }
    }

        override fun onStart() {
            super.onStart()
            val currentUser = auth.currentUser
            if (currentUser != null) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        private fun loginUser(email: String, password: String) {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                }
            }
        }

}