package com.capstone.urbanmove.data.remote.models

data class UsuarioDataResponse(
    val id_usuario: String,
    val nombres: String,
    val apellidos: String?,
    val foto_perfil: String?
)
