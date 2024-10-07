package com.capstone.urbanmove.data.remote.models

data class RegisterUserRequest(
    val nombres: String,
    val apellidos: String?,
    val fecha_nacimiento: String?,
    val email: String,
    val password: String?
)