package com.capstone.urbanmove.domain.usecase

import com.capstone.urbanmove.data.remote.models.VerifyAccountRequest
import com.capstone.urbanmove.data.repository.UserRepository

class VerifyAccountUserUseCase {
    private val apiRepository = UserRepository()
    suspend operator fun invoke(request: VerifyAccountRequest): Any? {
        return apiRepository.verifyAccount(request)
    }
}