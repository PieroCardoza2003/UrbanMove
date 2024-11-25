package com.capstone.urbanmove.utils

object Constants {
    const val NAMEPREFERENCES = "urbmve_prefs"
    const val ACCESS_TOKEN = "access_token"
    const val REFRESH_TOKEN = "refresh_token"

    private const val BASE_URL_URBANMOVE = "https://urbanmove-api.onrender.com"
    private const val BASE_URL_URBANMOVE_local = "http://192.168.1.45:3000"

    private const val BASE_WS_URBANMOVE = "wss://urbanmove-websocket.onrender.com"
    private const val BASE_WS_URBANMOVE_local = "ws://192.168.1.45:4000"

    fun getBaseUrlUrbanmoveApi(): String = BASE_URL_URBANMOVE
    fun getBaseWsUrbanmove(): String = BASE_WS_URBANMOVE
}