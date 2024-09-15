package com.capstone.urbanmove.presentation.ui.emaillogin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.urbanmove.databinding.ActivityPhoneLoginBinding
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityPhoneLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var verificationId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhoneLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Inicializar FirebaseAuth
        auth = FirebaseAuth.getInstance()

        //Country Code Picker
    }
}