package com.capstone.urbanmove.presentation.ui.emaillogin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.urbanmove.databinding.ActivityPhoneLoginBinding
import com.capstone.urbanmove.presentation.ui.recovery.AccountRevocery
import com.capstone.urbanmove.presentation.ui.dateaccount.DateUser

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityPhoneLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhoneLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.forgotpassword.setOnClickListener {
            val intent = Intent(this, AccountRevocery::class.java)
            startActivity(intent)
        }

        binding.register.setOnClickListener{
            val intent = Intent(this, DateUser::class.java)
            startActivity(intent)
        }
    }
}