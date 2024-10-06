package com.capstone.urbanmove

import android.app.Application
import com.capstone.urbanmove.utils.PreferencesManager

class MainApplication: Application() {

    companion object {
        lateinit var preferencesManager: PreferencesManager
    }
    override fun onCreate() {
        super.onCreate()
        accessToSharedPreferences()
    }

    private fun accessToSharedPreferences(){
        preferencesManager = PreferencesManager(applicationContext)
    }
}