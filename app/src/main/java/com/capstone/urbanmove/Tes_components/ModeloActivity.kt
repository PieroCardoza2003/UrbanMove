package com.capstone.urbanmove.Tes_components

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.capstone.urbanmove.R
import com.capstone.urbanmove.databinding.ActivityModeloBinding

class ModeloActivity : AppCompatActivity(), ModeloFragment.OnItemSelectedListener {
    private lateinit var binding: ActivityModeloBinding
    private var selectedIdModelo: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityModeloBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvBuscadorModelo.setOnClickListener {
            val modeloFragment = ModeloFragment()
            modeloFragment.show(supportFragmentManager, modeloFragment.tag)
        }
    }

    override fun onItemSelected(item: Modelo) {
        // Para guardar el id_modelo para futuras llamadas o usos
        // selectedIdModelo = item.id_modelo

        findViewById<TextView>(R.id.tvBuscadorModelo).text = item.id_modelo.toString()
    }
}
