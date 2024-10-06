package com.capstone.urbanmove.data.remote.models

data class RegisterUserRequest(
    val nombres: String,
    val apellido: String?,
    val fecha_nacimiento: String?,
    val email: String,
    val password: String
)
