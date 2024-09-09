package com.capstone.urbanmove.presentation.ui.phonelogin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.urbanmove.databinding.ActivityPhoneLoginBinding
import com.capstone.urbanmove.presentation.ui.otpverification.OtpVerification
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider

class PhoneLogin : AppCompatActivity() {
    private lateinit var binding: ActivityPhoneLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhoneLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Country Code Picker
        binding.countryPicker.setOnCountryChangeListener {
            val country_code = "+" + binding.countryPicker.selectedCountryCode
            binding.countryCode.setText(country_code)
        }

        //Capturar_telefono

        binding.btngetOTP.setOnClickListener {
            val teléfono = binding.telFono.text.toString().trim()

            if (teléfono.isEmpty()){
                Toast.makeText(this, "Ingresa tú teléfono",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            binding.progressBar.visibility = View.VISIBLE
            binding.btngetOTP.visibility = View.INVISIBLE


            val intent = Intent(this, OtpVerification::class.java)
            intent.putExtra("teléfono",teléfono)
            startActivity(intent)

        }


    }
}