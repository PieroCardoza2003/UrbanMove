package com.capstone.urbanmove.domain.usecase

import com.capstone.urbanmove.data.repository.UserRepository
import com.capstone.urbanmove.presentation.ui.home_user.conductor.registerDriver.privado.vehicle.objects.VehicleColor
import com.capstone.urbanmove.presentation.ui.home_user.conductor.registerDriver.privado.vehicle.objects.VehicleMarca
import com.capstone.urbanmove.presentation.ui.home_user.conductor.registerDriver.privado.vehicle.objects.VehicleModelo

class ListRegisterVehicle {
    val repository = UserRepository()

    suspend fun getListaMarcas(): List<VehicleMarca> {
        return repository.getListaMarcas()
    }

    suspend fun getListaModelos(id: Int): List<VehicleModelo> {
        return repository.getListaModelos(id)
    }

    suspend fun getListaColores(): List<VehicleColor> {
        return repository.getListaColores()
    }
}