package com.capstone.urbanmove.data.remote.user

import com.capstone.urbanmove.data.remote.RetrofitHelper
import com.capstone.urbanmove.data.remote.models.ConductorEmpresaRequest
import com.capstone.urbanmove.data.remote.models.ConductorPrivadoRequest
import com.capstone.urbanmove.data.remote.models.LoginGoogleRequest
import com.capstone.urbanmove.data.remote.models.LoginResponse
import com.capstone.urbanmove.data.remote.models.LoginUserRequest
import com.capstone.urbanmove.data.remote.models.NewPasswordRequest
import com.capstone.urbanmove.data.remote.models.RegisterUserRequest
import com.capstone.urbanmove.data.remote.models.VerifyAccountRequest
import com.capstone.urbanmove.data.remote.models.VerifyCodeRequest
import com.capstone.urbanmove.data.remote.user_auth.SessionApiClient
import com.capstone.urbanmove.presentation.ui.home_user.conductor.registerDriver.privado.vehicle.objects.VehicleColor
import com.capstone.urbanmove.presentation.ui.home_user.conductor.registerDriver.privado.vehicle.objects.VehicleMarca
import com.capstone.urbanmove.presentation.ui.home_user.conductor.registerDriver.privado.vehicle.objects.VehicleModelo
import com.capstone.urbanmove.presentation.ui.home_user.pasajero.mapview.bottomsheet.models.Ruta
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

    suspend fun getListaMarcas(): List<VehicleMarca> {
        return withContext(Dispatchers.IO){
            val response = retrofit.create(SessionApiClient::class.java).getListaMarcas()
            response.body() ?: emptyList()
        }
    }

    suspend fun getListaModelos(id: Int): List<VehicleModelo> {
        return withContext(Dispatchers.IO){
            val response = retrofit.create(SessionApiClient::class.java).getListaModelos(id)
            response.body() ?: emptyList()
        }
    }

    suspend fun getListaColores(): List<VehicleColor> {
        return withContext(Dispatchers.IO){
            val response = retrofit.create(SessionApiClient::class.java).getListaColores()
            response.body() ?: emptyList()
        }
    }

    suspend fun getRutasPasajero(): List<Ruta> {
        return withContext(Dispatchers.IO){
            val response = retrofit.create(SessionApiClient::class.java).getRutasPasajero()
            response.body() ?: emptyList()
        }
    }

    suspend fun getRutasConductorPrivado(): List<Ruta> {
        return withContext(Dispatchers.IO){
            val response = retrofit.create(SessionApiClient::class.java).getRutasConductorPrivado()
            response.body() ?: emptyList()
        }
    }

    suspend fun getRutasConductorEmpresa(id: String): List<Ruta> {
        return withContext(Dispatchers.IO){
            val response = retrofit.create(SessionApiClient::class.java).getRutasConductorEmpresa(id)
            response.body() ?: emptyList()
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

    suspend fun createConductorPrivado(
        conductor: ConductorPrivadoRequest,
        fotoperfil: MultipartBody.Part?,
        licenciaFrontal: MultipartBody.Part,
        licenciaReverso: MultipartBody.Part
    ): Any? {
        return withContext(Dispatchers.IO){
            val response = retrofit.create(UserApiClient::class.java).createConductorPrivado(
                id_usuario = conductor.id_usuario,
                nombre = conductor.nombre,
                apellido = conductor.apellido,
                fecha_nacimiento = conductor.fecha_nacimiento,
                foto_perfil = conductor.foto_perfil,
                numero_licencia = conductor.numero_licencia,
                fecha_vencimiento = conductor.fecha_vencimiento,
                numero_placa = conductor.numero_placa,
                marca_vehiculo = conductor.marca_vehiculo,
                modelo_vehiculo = conductor.modelo_vehiculo,
                color_vehiculo = conductor.color_vehiculo,
                fotoperfil = fotoperfil,
                licencia_frontal = licenciaFrontal,
                licencia_reverso = licenciaReverso
            )
            response.body()
        }
    }
}