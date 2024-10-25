package com.capstone.urbanmove.data.remote

import com.capstone.urbanmove.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.getBaseUrlUrbanmoveApi())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}