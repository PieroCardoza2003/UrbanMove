package com.capstone.urbanmove.data.remote

import com.capstone.urbanmove.utils.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelperAuth {

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.getBaseUrlUrbanmoveApi())
            .addConverterFactory(GsonConverterFactory.create())
            .client(getClient())
            .build()
    }

    private fun getClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor())
            .authenticator(TokenAuthenticator())
            .build()
    }
}