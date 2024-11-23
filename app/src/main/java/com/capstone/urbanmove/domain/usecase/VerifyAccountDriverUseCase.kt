package com.capstone.urbanmove.domain.usecase

import com.capstone.urbanmove.data.remote.models.VerifyDriverAccountResponse
import com.capstone.urbanmove.data.repository.UserAuthRepository

class VerifyAccountDriverUseCase {
    val repository = UserAuthRepository()
    suspend operator fun invoke(): VerifyDriverAccountResponse? {
        return repository.verifyAccountDriver()
    }
}