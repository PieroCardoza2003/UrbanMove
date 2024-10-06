package com.capstone.urbanmove.domain.usecase

import com.capstone.urbanmove.data.repository.UserRepository
import com.capstone.urbanmove.data.remote.models.RegisterUserRequest

class RegisterUserUseCase {
    private val apiRepository = UserRepository()

    suspend operator fun invoke(user: RegisterUserRequest): Any? {
        return  apiRepository.createUser(user)
    }

}