package com.capstone.urbanmove.data.remote.models

data class VerifyCodeRequest(
    val email: String,
    val code: String
)
