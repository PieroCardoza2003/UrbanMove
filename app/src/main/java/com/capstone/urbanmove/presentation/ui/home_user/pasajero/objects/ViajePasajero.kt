package com.capstone.urbanmove.presentation.ui.home_user.pasajero.objects

data class ViajePasajero(
    var id_pasajero: String,
    var transportes: String, // sera una lista de 0 a 3 transportes
    var trayectoria: String, // ida o vuelta
    var alcance: String // 500.0 a 1000.0
)
