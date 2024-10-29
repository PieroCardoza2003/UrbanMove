package com.capstone.urbanmove

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.capstone.urbanmove.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MatriculasFragment.OnItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    private var selectedIdMarca: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvBuscadorMatriculas.setOnClickListener {
            val matriculasFragment = MatriculasFragment()
            matriculasFragment.show(supportFragmentManager, matriculasFragment.tag)
    }

}
    override fun onItemSelected(item: Marca) {
        // Para guardar el id_marca para futuras llamadas o usos
        //selectedIdMarca = item.id_marca

        findViewById<TextView>(R.id.tvBuscadorMatriculas).text = item.marca
    }
    fun int_navoption(v: View?){
        val i = Intent(this,NavOptions::class.java)
        startActivity(i)
    }
}
