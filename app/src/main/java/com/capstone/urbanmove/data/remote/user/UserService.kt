package com.capstone.urbanmove.data.remote.user

import com.capstone.urbanmove.data.remote.RetrofitHelper
import com.capstone.urbanmove.data.remote.models.ConductorEmpresaRequest
import com.capstone.urbanmove.data.remote.models.LoginGoogleRequest
import com.capstone.urbanmove.data.remote.models.LoginResponse
import com.capstone.urbanmove.data.remote.models.LoginUserRequest
import com.capstone.urbanmove.data.remote.models.NewPasswordRequest
import com.capstone.urbanmove.data.remote.models.RegisterUserRequest
import com.capstone.urbanmove.data.remote.models.VerifyAccountRequest
import com.capstone.urbanmove.data.remote.models.VerifyCodeRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody

class UserService {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun createUser(request: RegisterUserRequest): Any? {
        return withContext(Dispatchers.IO){
            val response = retrofit.create(UserApiClient::class.java).createUser(request)
            response.body()
        }
    }

    suspend fun verifyAccount(request: VerifyAccountRequest): Any? {
        return withContext(Dispatchers.IO){
            val response = retrofit.create(UserApiClient::class.java).verifyAccount(request)
            response.body()
        }
    }

    suspend fun newPassword(request: NewPasswordRequest): Any? {
        return withContext(Dispatchers.IO){
            val response = retrofit.create(UserApiClient::class.java).newPasswordUser(request)
            response.body()
        }
    }

    suspend fun verifyCode(request: VerifyCodeRequest): Any? {
        return withContext(Dispatchers.IO){
            val response = retrofit.create(UserApiClient::class.java).verifyCode(request)
            response.body()
        }
    }

    suspend fun loginUser(request: LoginUserRequest): LoginResponse?{
        return withContext(Dispatchers.IO){
            val response = retrofit.create(UserApiClient::class.java).loginUser(request)
            response.body()
        }
    }

    suspend fun loginGoogle(request: LoginGoogleRequest): LoginResponse?{
        return withContext(Dispatchers.IO){
            val response = retrofit.create(UserApiClient::class.java).loginGoogle(request)
            response.body()
        }
    }


    suspend fun createConductorEmpresa(
        conductor: ConductorEmpresaRequest,
        fotoperfil: MultipartBody.Part?,
        licenciaFrontal: MultipartBody.Part,
        licenciaReverso: MultipartBody.Part
    ): Any? {
        return withContext(Dispatchers.IO){
            val response = retrofit.create(UserApiClient::class.java).createConductorEmpresa(
                id_usuario = conductor.id_usuario,
                nombre = conductor.nombre,
                apellido = conductor.apellido,
                fecha_nacimiento = conductor.fecha_nacimiento,
                foto_perfil = conductor.foto_perfil,
                numero_licencia = conductor.numero_licencia,
                fecha_vencimiento = conductor.fecha_vencimiento,
                codigo_empleado = conductor.codigo_empleado,
                fotoperfil = fotoperfil,
                licencia_frontal = licenciaFrontal,
                licencia_reverso = licenciaReverso
            )
            response.body()
        }
    }

}