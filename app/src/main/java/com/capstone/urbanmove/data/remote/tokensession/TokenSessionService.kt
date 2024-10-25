package com.duckyroute.duckyroute.data.remote.api.tokensession


import com.capstone.urbanmove.data.remote.RetrofitHelper
import com.capstone.urbanmove.data.remote.models.ResponseToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TokenSessionService {

    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getNewAccessToken(token: ResponseToken): ResponseToken? {
        return withContext(Dispatchers.IO){
            val response = retrofit.create(TokenSessionApiClient::class.java).getNewAccessToken(token)
            response.body()
        }
    }
}