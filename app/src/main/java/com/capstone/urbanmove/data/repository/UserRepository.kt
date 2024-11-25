package com.capstone.urbanmove.data.repository

import com.capstone.urbanmove.data.remote.models.ConductorEmpresaRequest
import com.capstone.urbanmove.data.remote.models.ConductorPrivadoRequest
import com.capstone.urbanmove.data.remote.models.LoginGoogleRequest
import com.capstone.urbanmove.data.remote.models.LoginResponse
import com.capstone.urbanmove.data.remote.models.LoginUserRequest
import com.capstone.urbanmove.data.remote.models.NewPasswordRequest
import com.capstone.urbanmove.data.remote.user.UserService
import com.capstone.urbanmove.data.remote.models.RegisterUserRequest
import com.capstone.urbanmove.data.remote.models.VerifyAccountRequest
import com.capstone.urbanmove.data.remote.models.VerifyCodeRequest
import okhttp3.MultipartBody

class UserRepository {
    private val api = UserService()

    suspend fun createUser(user: RegisterUserRequest): Any? {
        return api.createUser(user)
    }

    suspend fun newPassword(user: NewPasswordRequest): Any? {
        return api.newPassword(user)
    }

    suspend fun verifyAccount(request: VerifyAccountRequest): Any? {
        return api.verifyAccount(request)
    }

    suspend fun verifyCode(request: VerifyCodeRequest): Any? {
        return api.verifyCode(request)
    }

    suspend fun loginUser(request: LoginUserRequest): LoginResponse? {
        return api.loginUser(request)
    }

    suspend fun loginGoogle(request: LoginGoogleRequest): LoginResponse?{
        return api.loginGoogle(request)
    }

    suspend fun createConductorEmpresa(
        conductor: ConductorEmpresaRequest,
        fotoperfil: MultipartBody.Part?,
        licenciaFrontal: MultipartBody.Part,
        licenciaReverso: MultipartBody.Part
    ): Any?{
        return api.createConductorEmpresa(
            conductor = conductor,
            fotoperfil = fotoperfil,
            licenciaFrontal = licenciaFrontal,
            licenciaReverso = licenciaReverso
        )
    }

    suspend fun createConductorPrivado(
        conductor: ConductorPrivadoRequest,
        fotoperfil: MultipartBody.Part?,
        licenciaFrontal: MultipartBody.Part,
        licenciaReverso: MultipartBody.Part
    ): Any?{
        return api.createConductorPrivado(
            conductor = conductor,
            fotoperfil = fotoperfil,
            licenciaFrontal = licenciaFrontal,
            licenciaReverso = licenciaReverso
        )
    }
}