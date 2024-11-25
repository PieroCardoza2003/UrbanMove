package com.capstone.urbanmove.presentation.ui.home_user.conductor

import com.google.android.gms.maps.model.LatLng

data class Detectado(
    var id_usuario: String,
    var location: LatLng
)
