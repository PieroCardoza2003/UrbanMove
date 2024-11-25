package com.capstone.urbanmove.presentation.ui.home_user.pasajero.mapview.bottomsheet.models

data class Ruta(
    var id_ruta: Int,
    var letra_ruta: String,
    var empresa: String,
    var id_transporte: Int,
    var tipo_transporte: String
)