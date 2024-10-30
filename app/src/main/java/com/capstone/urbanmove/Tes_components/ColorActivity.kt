package com.capstone.urbanmove.Tes_components

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.capstone.urbanmove.R
import com.capstone.urbanmove.databinding.ActivityColorBinding

class ColorActivity : AppCompatActivity(), ColorFragment.OnItemSelectedListener {
    private lateinit var binding: ActivityColorBinding
    private var selectedIdColor: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityColorBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onItemSelected(item: Color) {
        // Para guardar el id_color para futuras llamadas o usos
        // selectedIdColor = item.id_color
    }
}
