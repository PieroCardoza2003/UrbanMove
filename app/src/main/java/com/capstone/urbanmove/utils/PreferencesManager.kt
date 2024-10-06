package com.capstone.urbanmove.utils

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.capstone.urbanmove.data.remote.models.LoginResponse

class PreferencesManager(context: Context) {

    private val masterKey = MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()

    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        Constants.NAMEPREFERENCES,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun setSession(accesstoken: String, refreshtoken: String){
        setKey(Constants.ACCESS_TOKEN, accesstoken)
        setKey(Constants.REFRESH_TOKEN, refreshtoken)
    }

    fun getSession(): LoginResponse? {
        val accesstoken = getKey(Constants.ACCESS_TOKEN)
        val refreshtoken = getKey(Constants.REFRESH_TOKEN)

        if (accesstoken.isNullOrEmpty() || refreshtoken.isNullOrEmpty())
            return null
        return LoginResponse(accesstoken, refreshtoken)
    }

    private fun setKey(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    private fun getKey(key: String): String? {
        return sharedPreferences.getString(key, null)
    }
}