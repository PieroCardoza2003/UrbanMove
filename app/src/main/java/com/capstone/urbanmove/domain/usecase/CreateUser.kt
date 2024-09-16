package com.capstone.urbanmove.domain.usecase

import android.util.Log
import com.capstone.urbanmove.data.repository.remote.UserRepository
import com.capstone.urbanmove.domain.entity.UserBody
import com.capstone.urbanmove.domain.entity.UserResponse

class CreateUser {
    private val apiRepository = UserRepository()

    suspend operator fun invoke(user: UserBody): Any? {
        Log.d("prints", "create use case")
        return  apiRepository.createUser(user)
    }

}