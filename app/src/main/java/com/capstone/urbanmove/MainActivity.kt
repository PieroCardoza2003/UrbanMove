package com.capstone.urbanmove

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.capstone.urbanmove.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MatriculasFragment.OnItemSelectedListener {
    private lateinit var binding: ActivityMainBinding

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
    override fun onItemSelected(item: String) {
        binding.tvBuscadorMatriculas.text = item
    }
    fun int_navoption(v: View?){
        val i = Intent(this,NavOptions::class.java)
        startActivity(i)
    }
}
