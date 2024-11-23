package com.capstone.urbanmove.data.remote.models

data class VerifyDriverAccountResponse(
    val id_usuario: String,
    val nombres: String,
    val apellidos: String?,
    val fecha_nacimiento: String?,
    val foto_perfil: String?,
    val id_conductor: String?,
    val tipo_conductor: String?
)

