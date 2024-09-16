package com.capstone.urbanmove.data.remote.user

import com.capstone.urbanmove.domain.entity.UserBody
import com.capstone.urbanmove.domain.entity.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApiClient {

    @POST("/api/user/create")
    suspend fun createUser(@Body request: UserBody): Response<*>

}
