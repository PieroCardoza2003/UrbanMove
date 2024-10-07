package com.capstone.urbanmove.domain.usecase

import com.capstone.urbanmove.data.remote.models.VerifyCodeRequest
import com.capstone.urbanmove.data.repository.UserRepository

class VerifyCodeUserUseCase {
    private val apiRepository = UserRepository()
    suspend operator fun invoke(request: VerifyCodeRequest): Any? {
        return apiRepository.verifyCode(request)
    }
}