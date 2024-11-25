package com.capstone.urbanmove.data.remote.user_auth

import com.capstone.urbanmove.data.remote.models.UsuarioDataResponse
import com.capstone.urbanmove.data.remote.models.VerifyDriverAccountResponse
import com.capstone.urbanmove.data.remote.models.VerifySessionResponse
import com.capstone.urbanmove.presentation.ui.home_user.conductor.registerDriver.privado.vehicle.objects.VehicleColor
import com.capstone.urbanmove.presentation.ui.home_user.conductor.registerDriver.privado.vehicle.objects.VehicleMarca
import com.capstone.urbanmove.presentation.ui.home_user.conductor.registerDriver.privado.vehicle.objects.VehicleModelo
import com.capstone.urbanmove.presentation.ui.home_user.pasajero.mapview.bottomsheet.models.Ruta
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SessionApiClient {

    @GET("/api/user/verify-session")
    suspend fun getSessionUser(): Response<VerifySessionResponse>

    @GET("/api/user/get-user")
    suspend fun getDataUsuario(): Response<UsuarioDataResponse>

    @GET("/api/user/verify-driver")
    suspend fun verifyAccountDriver(): Response<VerifyDriverAccountResponse>

    @GET("/api/vehicle/brands")
    suspend fun getListaMarcas(): Response<List<VehicleMarca>>

    @GET("/api/vehicle/models/get/{id}")
    suspend fun getListaModelos(@Path("id") idMarca: Int): Response<List<VehicleModelo>>

    @GET("/api/vehicle/colors")
    suspend fun getListaColores(): Response<List<VehicleColor>>

    @GET("/api/company/all-route-passenger")
    suspend fun getRutasPasajero(): Response<List<Ruta>>

    @GET("/api/company/all-route-driver-privado")
    suspend fun getRutasConductorPrivado(): Response<List<Ruta>>

    @GET("/api/company/all-route")
    suspend fun getRutasConductorEmpresa(@Query("empresaID") id: String): Response<List<Ruta>>
}