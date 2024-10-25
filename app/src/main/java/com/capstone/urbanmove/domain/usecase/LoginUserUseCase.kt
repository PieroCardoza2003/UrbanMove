package com.capstone.urbanmove.domain.usecase

import com.capstone.urbanmove.data.remote.models.LoginResponse
import com.capstone.urbanmove.data.remote.models.LoginUserRequest
import com.capstone.urbanmove.data.repository.UserRepository

class LoginUserUseCase {
    private val apiRepository = UserRepository()

    suspend operator fun invoke(request: LoginUserRequest): LoginResponse?{
        return apiRepository.loginUser(request)
    }
}