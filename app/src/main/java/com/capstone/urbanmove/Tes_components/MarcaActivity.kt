package com.capstone.urbanmove.Tes_components

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.capstone.urbanmove.R
import com.capstone.urbanmove.databinding.ActivityMarcaBinding

class MarcaActivity : AppCompatActivity(), MarcaFragment.OnItemSelectedListener {
    private lateinit var binding: ActivityMarcaBinding
    private var selectedIdMarca: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMarcaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvBuscadorMarca.setOnClickListener {
            val marcaFragment = MarcaFragment()
            marcaFragment.show(supportFragmentManager, marcaFragment.tag)
        }

    }
    override fun onItemSelected(item: Marca) {
        // Para guardar el id_marca para futuras llamadas o usos
        //selectedIdMarca = item.id_marca

        findViewById<TextView>(R.id.tvBuscadorMarca).text = item.marca
    }
}
