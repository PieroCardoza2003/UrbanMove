package com.capstone.urbanmove.presentation.ui.home_user.conductor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.capstone.urbanmove.R
import com.capstone.urbanmove.databinding.ActivityDriverBinding
import com.capstone.urbanmove.databinding.NavHeaderBinding
import com.capstone.urbanmove.domain.entity.Result
import com.capstone.urbanmove.presentation.ui.common.ErrorActivity
import com.capstone.urbanmove.presentation.ui.home_user.pasajero.PassengerActivity
import com.capstone.urbanmove.presentation.ui.login_user.LoginActivity

class DriverActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDriverBinding
    private val viewModel: DriverViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDriverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.drawerLayoutDriver.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        viewModel.loaddata()

        viewModel.result.observe(this){ result ->
            when(result) {
                Result.SUCCESS -> {
                    val drawerBinding = NavHeaderBinding.bind(binding.navigationDrawer.getHeaderView(0))
                    drawerBinding.texviewNombrePasajero.text = viewModel.driver_data.value!!.nombres
                    drawerBinding.textviewApellidosPasajero.text = viewModel.driver_data.value!!.apellidos ?: ""
                    if (viewModel.driver_data.value!!.foto_perfil != null) {
                        Glide.with(this)
                            .load(viewModel.driver_data.value!!.foto_perfil)
                            .into(drawerBinding.imagenPerfilPasajero)
                    }
                    binding.drawerLayoutDriver.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                    binding.fragmentcontainerMapDriver.findNavController().navigate(R.id.action_to_map_driver)
                }
                Result.UNSUCCESS -> {
                    binding.fragmentcontainerMapDriver.findNavController().navigate(R.id.action_to_tipo_conductor)
                }
                else -> {
                    val intent = Intent(this, ErrorActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }

        binding.buttonModePasajero.setOnClickListener {
            val intent = Intent(this, PassengerActivity::class.java)
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
            binding.drawerLayoutDriver.closeDrawer(GravityCompat.START)
            true
        }


    }
}