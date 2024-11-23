package com.capstone.urbanmove.data.remote.models

data class ConductorEmpresaRequest(
    var id_usuario: String,
    var nombre: String,
    var apellido: String,
    var fecha_nacimiento: String,
    var foto_perfil: String?,
    var numero_licencia: String,
    var fecha_vencimiento: String,
    var codigo_empleado: String
)