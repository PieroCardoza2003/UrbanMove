package com.capstone.urbanmove.presentation.ui.home_user.pasajero

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.capstone.urbanmove.R
import com.capstone.urbanmove.databinding.ActivityPassengerBinding

class PassengerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPassengerBinding
    private val viewModel: PassengerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPassengerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}