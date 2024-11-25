package com.capstone.urbanmove.domain.usecase

import com.capstone.urbanmove.data.repository.UserRepository
import com.capstone.urbanmove.presentation.ui.home_user.pasajero.mapview.bottomsheet.models.Ruta

class GetRutasUseCase {
    val repository = UserRepository()

    suspend fun rutas_pasajero(): List<Ruta> {
        return repository.getRutasPasajero()
    }

    suspend fun rutas_conductor_privado(): List<Ruta> {
        return repository.getRutasConductorPrivado()
    }

    suspend fun rutas_conductor_empresa(id: String): List<Ruta> {
        return repository.getRutasConductorEmpresa(id)
    }
}