package com.capstone.urbanmove.data.remote.user

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApiClient {

    @POST("/user/create")
    suspend fun createUser(@Body request: ): Response<>

}