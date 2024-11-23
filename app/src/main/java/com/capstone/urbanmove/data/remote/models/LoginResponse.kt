package com.capstone.urbanmove.data.remote.models

data class LoginResponse(
    val access_token: String,
    val refresh_token: String,
    val role: String
)
