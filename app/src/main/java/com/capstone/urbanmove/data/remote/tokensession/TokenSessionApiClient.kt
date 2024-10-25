package com.duckyroute.duckyroute.data.remote.api.tokensession


import com.capstone.urbanmove.data.remote.models.ResponseToken
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TokenSessionApiClient {
    @POST("/api/user/access-token")
    suspend fun getNewAccessToken(@Body request: ResponseToken): Response<ResponseToken>
}