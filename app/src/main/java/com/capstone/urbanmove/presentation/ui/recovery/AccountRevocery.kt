package com.capstone.urbanmove.presentation.ui.recovery

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.urbanmove.databinding.ActivityAccountRevoceryBinding
import com.capstone.urbanmove.presentation.ui.emaillogin.Login
import com.capstone.urbanmove.presentation.ui.otpverification.OtpVerification

class AccountRevocery : AppCompatActivity() {
    private lateinit var binding: ActivityAccountRevoceryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountRevoceryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val messagereco = "Introduzca el código que enviamos a"

        binding.confirmmail.setOnClickListener {
            //Capturar email
            val email = binding.emailreco.text.toString()

            val intent = Intent(this,OtpVerification::class.java).apply {
                putExtra("EMAIL_KEY",email)
                putExtra("MSG",messagereco)
            }
            startActivity(intent)

        }

        binding.cancelreco.setOnClickListener {
            val intent = Intent(this,Login::class.java)
            startActivity(intent)
        }

    }
}