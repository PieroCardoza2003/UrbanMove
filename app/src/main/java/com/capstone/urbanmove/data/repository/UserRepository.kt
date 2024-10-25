package com.capstone.urbanmove.data.repository

import com.capstone.urbanmove.data.remote.models.LoginGoogleRequest
import com.capstone.urbanmove.data.remote.models.LoginResponse
import com.capstone.urbanmove.data.remote.models.LoginUserRequest
import com.capstone.urbanmove.data.remote.models.NewPasswordRequest
import com.capstone.urbanmove.data.remote.user.UserService
import com.capstone.urbanmove.data.remote.models.RegisterUserRequest
import com.capstone.urbanmove.data.remote.models.VerifyAccountRequest
import com.capstone.urbanmove.data.remote.models.VerifyCodeRequest

class UserRepository {
    private val api = UserService()

    suspend fun createUser(user: RegisterUserRequest): Any? {
        return api.createUser(user)
    }

    suspend fun newPassword(user: NewPasswordRequest): Any? {
        return api.newPassword(user)
    }

    suspend fun verifyAccount(request: VerifyAccountRequest): Any? {
        return api.verifyAccount(request)
    }

    suspend fun verifyCode(request: VerifyCodeRequest): Any? {
        return api.verifyCode(request)
    }

    suspend fun loginUser(request: LoginUserRequest): LoginResponse? {
        return api.loginUser(request)
    }

    suspend fun loginGoogle(request: LoginGoogleRequest): LoginResponse?{
        return api.loginGoogle(request)
    }
}