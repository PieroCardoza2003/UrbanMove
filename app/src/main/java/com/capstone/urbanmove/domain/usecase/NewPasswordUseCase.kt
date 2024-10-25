package com.capstone.urbanmove.domain.usecase

import com.capstone.urbanmove.data.remote.models.NewPasswordRequest
import com.capstone.urbanmove.data.repository.UserRepository

class NewPasswordUseCase {
    private val apiRepository = UserRepository()

    suspend operator fun invoke(user: NewPasswordRequest): Any?{
        return apiRepository.newPassword(user)
    }

}