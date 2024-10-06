package com.capstone.urbanmove

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.capstone.urbanmove.presentation.ui.login_user.LoginActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        val intent=Intent(this,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}