package com.capstone.urbanmove.data.remote.user

import android.util.Log
import com.capstone.urbanmove.data.remote.RetrofitHelper
import com.capstone.urbanmove.domain.entity.UserBody
import com.capstone.urbanmove.domain.entity.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserService {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun createUser(request: UserBody): UserResponse? {
        return withContext(Dispatchers.IO){
            val response = retrofit.create(UserApiClient::class.java).createUser(request)
            Log.d("prints","${response.body()} ")
            response.body()
        }
    }

}