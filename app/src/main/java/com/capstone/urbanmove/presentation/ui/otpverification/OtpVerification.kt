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

        val email1 = intent.getStringExtra("EMAIL_KEY")
        binding.showmail.text=email1

        val msg1 = intent.getStringExtra("MSG")
        binding.messageverify.text=msg1

        verificarOTP()
    }

    private fun verificarOTP(){

        val otp = listOf(binding.code1,binding.code2,binding.code3,binding.code4,binding.code5)
        for (i in otp.indices) {
            otp[i].addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s.toString().trim().isNotEmpty() && i < otp.size-1){
                        otp[i + 1].requestFocus()
                    }
                }

                override fun afterTextChanged(s: Editable?) {

                }
            })
        }
    }
}