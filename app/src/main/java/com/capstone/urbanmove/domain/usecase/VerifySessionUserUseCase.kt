package com.capstone.urbanmove.domain.usecase

import com.capstone.urbanmove.data.remote.models.VerifySessionResponse
import com.capstone.urbanmove.data.repository.UserAuthRepository

class VerifySessionUserUseCase {

    val repository = UserAuthRepository()

    suspend operator fun invoke(): VerifySessionResponse? {
        return repository.getSessionUser()
    }

}