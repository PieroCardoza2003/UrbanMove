package com.capstone.urbanmove.domain.entity

import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.time.ZonedDateTime

data class UserResponse(
    @SerializedName("apellidos_usuario")
    val apellido: String?,
    @SerializedName("foto_usuario")
    val foto: String?,
    @SerializedName("fecha_nacimiento_usuario")
    val fechanacimiento: LocalDate?,
    @SerializedName("email_usuario")
    val correo: String,
    @SerializedName("fecha_registro_usuario")
    val fecharegistro: String?,
    @SerializedName("id_usuario")
    val idusuario: String,
    @SerializedName("nombres_usuario")
    val nombres: String,
    @SerializedName("password_usuario")
    val contraseña: String,
    @SerializedName("activo")
    val estado: String,
)

