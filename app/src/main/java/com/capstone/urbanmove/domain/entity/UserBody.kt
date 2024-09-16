package com.capstone.urbanmove.domain.entity

import com.google.gson.annotations.SerializedName

data class UserBody(
    @SerializedName("nombres_usuario")
    val nombres: String,
    @SerializedName("apellidos_usuario")
    val apellido: String?,
    @SerializedName("fecha_nacimiento_usuario")
    val fechanacimiento: String?,
    @SerializedName("email_usuario")
    val correo: String,
    @SerializedName("password_usuario")
    val contraseña: String,
)
