package com.capstone.urbanmove.presentation.ui.home_user.pasajero

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import com.bumptech.glide.Glide
import com.capstone.urbanmove.R
import com.capstone.urbanmove.databinding.ActivityPassengerBinding
import com.capstone.urbanmove.databinding.NavHeaderBinding
import com.capstone.urbanmove.domain.entity.Result
import com.capstone.urbanmove.presentation.ui.common.ErrorActivity
import com.capstone.urbanmove.presentation.ui.home_user.conductor.DriverActivity
import com.capstone.urbanmove.presentation.ui.login_user.LoginActivity

class PassengerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPassengerBinding
    private val viewModel: PassengerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPassengerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.loaddata()

        viewModel.result.observe(this){ result ->
            when(result) {
                Result.SUCCESS ->{
                    val drawerBinding = NavHeaderBinding.bind(binding.navigationDrawer.getHeaderView(0))
                    drawerBinding.texviewNombrePasajero.text = viewModel.user.value!!.nombres
                    drawerBinding.textviewApellidosPasajero.text = viewModel.user.value!!.apellidos ?: ""
                    if (viewModel.user.value!!.foto_perfil != null) {
                        Glide.with(this)
                            .load(viewModel.user.value!!.foto_perfil)
                            .into(drawerBinding.imagenPerfilPasajero)
                    }
                }
                else -> {
                    val intent = Intent(this, ErrorActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }

        binding.buttonModeConductor.setOnClickListener {
            val intent = Intent(this, DriverActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.navigationDrawer.setNavigationItemSelectedListener { item ->
            item.isCheckable = false
            when(item.itemId){
                R.id.nav_map -> Toast.makeText(applicationContext,"Mapa", Toast.LENGTH_SHORT).show()
                R.id.nav_settings -> Toast.makeText(applicationContext,"Configuraciones", Toast.LENGTH_SHORT).show()
                R.id.nav_help -> Toast.makeText(applicationContext,"Ayuda", Toast.LENGTH_SHORT).show()
                R.id.nav_logout -> {
                    viewModel.closeSession()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }


    }




}