package com.capstone.urbanmove.presentation.ui.home_user.conductor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.urbanmove.R
import com.capstone.urbanmove.databinding.ActivityDriverBinding

class DriverActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDriverBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDriverBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}