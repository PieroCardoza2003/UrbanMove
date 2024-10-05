package com.capstone.urbanmove.presentation.ui.otpverification

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.capstone.urbanmove.R
import com.capstone.urbanmove.databinding.ActivityOtpVerificationBinding

class OtpVerification : AppCompatActivity() {
    private lateinit var binding: ActivityOtpVerificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.showmail.text = "emailtest@gmail.com"

        binding.buttomClose.setOnClickListener {
            finish()
        }

        verificarOTP()
    }

    private fun verificarOTP() {

    }
}