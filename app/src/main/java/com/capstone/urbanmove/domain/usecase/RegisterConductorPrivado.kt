package com.capstone.urbanmove.domain.usecase

import com.capstone.urbanmove.data.remote.models.ConductorPrivadoRequest
import com.capstone.urbanmove.data.repository.UserRepository
import okhttp3.MultipartBody

class RegisterConductorPrivado {

    val repository = UserRepository()

    suspend operator fun invoke(
        conductor: ConductorPrivadoRequest,
        fotoperfil: MultipartBody.Part?,
        licenciaFrontal: MultipartBody.Part,
        licenciaReverso: MultipartBody.Part
    ): Any? {
        return repository.createConductorPrivado(
            conductor = conductor,
            fotoperfil = fotoperfil,
            licenciaFrontal = licenciaFrontal,
            licenciaReverso = licenciaReverso
        )
    }
}