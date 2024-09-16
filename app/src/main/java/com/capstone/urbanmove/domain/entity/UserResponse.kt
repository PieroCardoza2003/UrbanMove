package com.capstone.urbanmove.domain.entity

import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.time.ZonedDateTime

data class UserResponse(
    @SerializedName("id_usuario")
    val idusuario: String
)

