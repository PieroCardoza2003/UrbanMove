package com.capstone.urbanmove.data.remote.user

import com.capstone.urbanmove.data.remote.models.LoginGoogleRequest
import com.capstone.urbanmove.data.remote.models.LoginResponse
import com.capstone.urbanmove.data.remote.models.LoginUserRequest
import com.capstone.urbanmove.data.remote.models.RegisterUserRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApiClient {

    @POST("/api/user/register")
    suspend fun createUser(@Body request: RegisterUserRequest): Response<*>

    @POST("/api/user/login")
    suspend fun loginUser(@Body request: LoginUserRequest): Response<LoginResponse>

    @POST("/api/user/login-google")
    suspend fun loginGoogle(@Body request: LoginGoogleRequest): Response<LoginResponse>

}
