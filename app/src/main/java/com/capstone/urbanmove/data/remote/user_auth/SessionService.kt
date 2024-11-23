package com.capstone.urbanmove.data.remote.user_auth

import com.capstone.urbanmove.data.remote.RetrofitHelperAuth
import com.capstone.urbanmove.data.remote.models.UsuarioDataResponse
import com.capstone.urbanmove.data.remote.models.VerifyDriverAccountResponse
import com.capstone.urbanmove.data.remote.models.VerifySessionResponse
import com.capstone.urbanmove.presentation.ui.home_user.conductor.registerDriver.privado.vehicle.objects.VehicleColor
import com.capstone.urbanmove.presentation.ui.home_user.conductor.registerDriver.privado.vehicle.objects.VehicleMarca
import com.capstone.urbanmove.presentation.ui.home_user.conductor.registerDriver.privado.vehicle.objects.VehicleModelo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SessionService {

    private val retrofit = RetrofitHelperAuth.getRetrofit()

    suspend fun getSessionUser(): VerifySessionResponse?{
        return withContext(Dispatchers.IO){
            val response = retrofit.create(SessionApiClient::class.java).getSessionUser()
            response.body()
        }
    }

    suspend fun getDataUsuario(): UsuarioDataResponse?{
        return withContext(Dispatchers.IO){
            val response = retrofit.create(SessionApiClient::class.java).getDataUsuario()
            response.body()
        }
    }

    suspend fun verifyAccountDriver(): VerifyDriverAccountResponse?{
        return withContext(Dispatchers.IO){
            val response = retrofit.create(SessionApiClient::class.java).verifyAccountDriver()
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
}