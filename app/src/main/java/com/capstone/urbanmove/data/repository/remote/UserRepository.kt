package com.capstone.urbanmove.data.repository.remote

import android.util.Log
import com.capstone.urbanmove.data.remote.user.UserService
import com.capstone.urbanmove.domain.entity.UserBody

class UserRepository {
    private val api = UserService()

    suspend fun createUser(user: UserBody): Any? {
        Log.d("prints", "create repository")
        return api.createUser(user)
    }
}