package com.capstone.urbanmove.data.remote.user_auth

import com.capstone.urbanmove.data.remote.RetrofitHelperAuth
import com.capstone.urbanmove.data.remote.models.UsuarioDataResponse
import com.capstone.urbanmove.data.remote.models.VerifySessionResponse
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
}