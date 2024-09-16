package com.capstone.urbanmove

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.capstone.urbanmove.databinding.ActivityMainBinding
import com.capstone.urbanmove.presentation.ui.dateinformation.activity_date

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent(this, activity_date::class.java)
        startActivity(intent)
        finish()
    }
}
