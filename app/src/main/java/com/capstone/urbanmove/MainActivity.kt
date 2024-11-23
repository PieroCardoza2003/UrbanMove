package com.capstone.urbanmove

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.capstone.urbanmove.presentation.ui.common.ErrorActivity
import com.capstone.urbanmove.presentation.ui.home_user.conductor.DriverActivity
import com.capstone.urbanmove.presentation.ui.home_user.pasajero.PassengerActivity
import com.capstone.urbanmove.presentation.ui.login_user.LoginActivity

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()

        splashScreen.setKeepOnScreenCondition{
            viewModel.isLogged.value == null
        }

        viewModel.isLogged.observe(this) { isLogged ->
            val intent = when (isLogged) {
                "PASAJERO" -> Intent(this, PassengerActivity::class.java)
                "CONDUCTOR" -> Intent(this, DriverActivity::class.java)
                "ERROR" -> Intent(this, ErrorActivity::class.java)
                else -> Intent(this, LoginActivity::class.java)
            }
            startActivity(intent)
            finish()
        }

        viewModel.checkIsLogged()
    }
}