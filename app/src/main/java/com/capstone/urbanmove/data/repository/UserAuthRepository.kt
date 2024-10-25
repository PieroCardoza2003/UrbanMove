package com.capstone.urbanmove.data.repository

import com.capstone.urbanmove.data.remote.models.UsuarioDataResponse
import com.capstone.urbanmove.data.remote.models.VerifySessionResponse
import com.capstone.urbanmove.data.remote.user_auth.SessionService

class UserAuthRepository {

    private val api = SessionService()

    suspend fun getSessionUser(): VerifySessionResponse? {
        return api.getSessionUser()
    }

    suspend fun getDataUsuario(): UsuarioDataResponse? {
        return api.getDataUsuario()
    }

}