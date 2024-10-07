package com.capstone.urbanmove.presentation.ui.common

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.urbanmove.R
import com.capstone.urbanmove.databinding.ActivityErrorBinding

class ErrorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityErrorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityErrorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonCerrarError.setOnClickListener{
            finishAffinity()
        }
    }
}