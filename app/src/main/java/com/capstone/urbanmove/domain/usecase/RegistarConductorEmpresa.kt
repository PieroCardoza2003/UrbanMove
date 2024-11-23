package com.capstone.urbanmove.domain.usecase

import com.capstone.urbanmove.data.remote.models.ConductorEmpresaRequest
import com.capstone.urbanmove.data.remote.models.UsuarioDataResponse
import com.capstone.urbanmove.data.repository.UserRepository
import okhttp3.MultipartBody

class RegistarConductorEmpresa {

    val repository = UserRepository()

    suspend operator fun invoke(
        conductor: ConductorEmpresaRequest,
        fotoperfil: MultipartBody.Part?,
        licenciaFrontal: MultipartBody.Part,
        licenciaReverso: MultipartBody.Part
    ): Any? {
        return repository.createConductorEmpresa(
            conductor = conductor,
            fotoperfil = fotoperfil,
            licenciaFrontal = licenciaFrontal,
            licenciaReverso = licenciaReverso
        )
    }
}