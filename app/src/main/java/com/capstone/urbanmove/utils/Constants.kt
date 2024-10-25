package com.capstone.urbanmove.utils

object Constants {
    const val NAMEPREFERENCES = "urbmve_prefs"
    const val ACCESS_TOKEN = "access_token"
    const val REFRESH_TOKEN = "refresh_token"
    private const val BASE_URL_URBANMOVE_remote = "https://urbanmove-api.onrender.com"
    private const val BASE_URL_URBANMOVE = "http://192.168.1.33:3000"

    fun getBaseUrlUrbanmoveApi(): String = BASE_URL_URBANMOVE
}