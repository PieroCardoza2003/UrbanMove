package com.capstone.urbanmove.data.remote

import android.util.Log
import com.capstone.urbanmove.MainApplication.Companion.preferencesManager
import com.capstone.urbanmove.data.remote.models.ResponseToken
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import com.duckyroute.duckyroute.data.remote.api.tokensession.TokenSessionService

class TokenAuthenticator : Authenticator{
    override fun authenticate(route: Route?, response: Response): Request? {
        synchronized(this){
            return runBlocking {
                val refreshToken = preferencesManager.getSession()?.refresh_token ?: ""
                val newToken = TokenSessionService().getNewAccessToken(ResponseToken(refreshToken))

                if(newToken != null){
                    preferencesManager.setSession(newToken.token, refreshToken)
                    response.request().newBuilder()
                        .header("Authorization", "Bearer ${newToken.token}")
                        .build()
                }else{
                    null
                }
            }
        }
    }
}