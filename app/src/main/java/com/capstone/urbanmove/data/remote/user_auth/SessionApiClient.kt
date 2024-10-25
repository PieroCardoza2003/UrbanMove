package com.capstone.urbanmove.data.remote.user_auth

import com.capstone.urbanmove.data.remote.models.UsuarioDataResponse
import com.capstone.urbanmove.data.remote.models.VerifySessionResponse
import retrofit2.Response
import retrofit2.http.GET

interface SessionApiClient {

    @GET("/api/user/verify-session")
    suspend fun getSessionUser(): Response<VerifySessionResponse>

    @GET("/api/user/get-user")
    suspend fun getDataUsuario(): Response<UsuarioDataResponse>

}