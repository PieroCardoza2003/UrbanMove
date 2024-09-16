package com.capstone.urbanmove.utils

object Constants {

    private const val BASE_URL_URBANMOVE = "https://urbanmove-api.onrender.com/api"
    private const val BASE_URL_URBANMOVE_LOCAL = "http://127.0.0.1:8000/api"
    fun getBaseUrlUrbanmoveApi(): String = BASE_URL_URBANMOVE
}