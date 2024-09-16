package com.capstone.urbanmove.data.remote.user

import com.capstone.urbanmove.data.remote.RetrofitHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserService {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun createUser(request: UserSessionRequest): UserSessionResponse? {
        return withContext(Dispatchers.IO){
            val response = retrofit.create(UserApiClient::class.java).createUser(request)
            response.body()
        }
    }

}