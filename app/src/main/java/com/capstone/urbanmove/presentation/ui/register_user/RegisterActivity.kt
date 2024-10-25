package com.capstone.urbanmove.presentation.ui.register_user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.capstone.urbanmove.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var bindig: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindig = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(bindig.root)
    }
}