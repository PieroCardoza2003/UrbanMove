package com.capstone.urbanmove.domain.entity

data class Usuario(
    val id_usuario: String,
    val nombres: String,
    val apellido: String?,
    val foto_perfil: String?,
    val email: String,
    val fecha_nacimiento: String?,
    val rol_actual: String
)