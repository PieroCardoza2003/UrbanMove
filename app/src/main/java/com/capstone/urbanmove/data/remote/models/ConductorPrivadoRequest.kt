package com.capstone.urbanmove.data.remote.models

data class ConductorPrivadoRequest(
    var id_usuario: String,
    var nombre: String,
    var apellido: String,
    var fecha_nacimiento: String,
    var foto_perfil: String?,
    var numero_licencia: String,
    var fecha_vencimiento: String,
    var numero_placa: String,
    var marca_vehiculo: String,
    var modelo_vehiculo: String,
    var color_vehiculo: String
)
