package com.capstone.urbanmove

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.capstone.urbanmove.presentation.ui.dateaccount.DateUser
import com.capstone.urbanmove.presentation.ui.emaillogin.Login
import com.capstone.urbanmove.presentation.ui.otpverification.OtpVerification

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_main)


        val intent=Intent(this,Login::class.java)
        startActivity(intent)
    }
}