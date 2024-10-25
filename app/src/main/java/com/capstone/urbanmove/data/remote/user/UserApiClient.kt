package com.capstone.urbanmove.data.remote.user

import com.capstone.urbanmove.data.remote.models.LoginGoogleRequest
import com.capstone.urbanmove.data.remote.models.LoginResponse
import com.capstone.urbanmove.data.remote.models.LoginUserRequest
import com.capstone.urbanmove.data.remote.models.NewPasswordRequest
import com.capstone.urbanmove.data.remote.models.RegisterUserRequest
import com.capstone.urbanmove.data.remote.models.VerifyAccountRequest
import com.capstone.urbanmove.data.remote.models.VerifyCodeRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApiClient {

    @POST("/api/user/register")
    suspend fun createUser(@Body request: RegisterUserRequest): Response<*>

    @POST("/api/user/new-password")
    suspend fun newPasswordUser(@Body request: NewPasswordRequest): Response<*>

    @POST("/api/user/login")
    suspend fun loginUser(@Body request: LoginUserRequest): Response<LoginResponse>

    @POST("/api/user/login-google")
    suspend fun loginGoogle(@Body request: LoginGoogleRequest): Response<LoginResponse>

    @POST("/api/user/verify-account")
    suspend fun verifyAccount(@Body request: VerifyAccountRequest): Response<*>

    @POST("/api/user/verify-code")
    suspend fun verifyCode(@Body request: VerifyCodeRequest): Response<*>


}
