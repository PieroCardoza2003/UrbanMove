package com.capstone.urbanmove.data.remote

import android.util.Log
import com.capstone.urbanmove.MainApplication.Companion.preferencesManager
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = preferencesManager.getSession()?.access_token ?: ""
        val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        return chain.proceed(request)
    }
}