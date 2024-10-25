package com.capstone.urbanmove.domain.usecase

import com.capstone.urbanmove.data.remote.models.LoginGoogleRequest
import com.capstone.urbanmove.data.remote.models.LoginResponse
import com.capstone.urbanmove.data.repository.UserRepository

class LoginGoogleUseCase {
    private val apiRepository = UserRepository()

    suspend operator fun invoke(request: LoginGoogleRequest): LoginResponse?{
        return apiRepository.loginGoogle(request)
    }
}