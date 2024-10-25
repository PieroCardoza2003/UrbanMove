package com.capstone.urbanmove.domain.usecase

import com.capstone.urbanmove.data.remote.models.UsuarioDataResponse
import com.capstone.urbanmove.data.remote.models.VerifySessionResponse
import com.capstone.urbanmove.data.repository.UserAuthRepository

class GetDataUserUseCase {
    val repository = UserAuthRepository()

    suspend operator fun invoke(): UsuarioDataResponse? {
        return repository.getDataUsuario()
    }
}