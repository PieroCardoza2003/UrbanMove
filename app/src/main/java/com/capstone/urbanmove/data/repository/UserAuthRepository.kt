package com.capstone.urbanmove.data.repository

import com.capstone.urbanmove.data.remote.models.UsuarioDataResponse
import com.capstone.urbanmove.data.remote.models.VerifyDriverAccountResponse
import com.capstone.urbanmove.data.remote.models.VerifySessionResponse
import com.capstone.urbanmove.data.remote.user_auth.SessionService
import com.capstone.urbanmove.presentation.ui.home_user.conductor.registerDriver.privado.vehicle.objects.VehicleColor
import com.capstone.urbanmove.presentation.ui.home_user.conductor.registerDriver.privado.vehicle.objects.VehicleMarca
import com.capstone.urbanmove.presentation.ui.home_user.conductor.registerDriver.privado.vehicle.objects.VehicleModelo

class UserAuthRepository {

    private val api = SessionService()

    suspend fun getSessionUser(): VerifySessionResponse? {
        return api.getSessionUser()
    }

    suspend fun getDataUsuario(): UsuarioDataResponse? {
        return api.getDataUsuario()
    }

    suspend fun verifyAccountDriver(): VerifyDriverAccountResponse? {
        return api.verifyAccountDriver()
    }

    suspend fun getListaMarcas(): List<VehicleMarca> {
        return api.getListaMarcas()
    }

    suspend fun getListaModelos(id: Int): List<VehicleModelo> {
        return api.getListaModelos(id)
    }

    suspend fun getListaColores(): List<VehicleColor> {
        return api.getListaColores()
    }
}