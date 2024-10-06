package com.capstone.urbanmove.presentation.ui.forgot_password_user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.capstone.urbanmove.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var bindig: ActivityForgotPasswordBinding
    private val viewModel: ForgotPasswordViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindig = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(bindig.root)
    }
}