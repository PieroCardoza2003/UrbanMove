package com.capstone.urbanmove.data.remote.user

import com.capstone.urbanmove.data.remote.RetrofitHelper
import com.capstone.urbanmove.data.remote.models.LoginGoogleRequest
import com.capstone.urbanmove.data.remote.models.LoginResponse
import com.capstone.urbanmove.data.remote.models.LoginUserRequest
import com.capstone.urbanmove.data.remote.models.RegisterUserRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserService {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun createUser(request: RegisterUserRequest): Any? {
        return withContext(Dispatchers.IO){
            val response = retrofit.create(UserApiClient::class.java).createUser(request)
            response.body()
        }
    }

    suspend fun loginUser(request: LoginUserRequest): LoginResponse?{
        return withContext(Dispatchers.IO){
            val response = retrofit.create(UserApiClient::class.java).loginUser(request)
            response.body()
        }
    }

    suspend fun loginGoogle(request: LoginGoogleRequest): LoginResponse?{
        return withContext(Dispatchers.IO){
            val response = retrofit.create(UserApiClient::class.java).loginGoogle(request)
            response.body()
        }
    }
}