package com.capstone.urbanmove.presentation.ui.phonelogin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.urbanmove.databinding.ActivityPhoneLoginBinding

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


    }
}