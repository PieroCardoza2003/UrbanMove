package com.capstone.urbanmove.presentation.ui.login_user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.urbanmove.data.remote.models.LoginGoogleRequest
import com.capstone.urbanmove.domain.usecase.LoginGoogleUseCase
import com.capstone.urbanmove.domain.usecase.LoginUserUseCase
import com.capstone.urbanmove.MainApplication.Companion.preferencesManager
import com.capstone.urbanmove.data.remote.models.LoginUserRequest
import com.capstone.urbanmove.domain.entity.Result
import kotlinx.coroutines.launch
import java.io.IOException

class LoginViewModel: ViewModel() {

    var loginUser = LoginUserUseCase()
    var loginGoogle = LoginGoogleUseCase()

    val result = MutableLiveData<Result>()

    fun loginUser(email: String, password: String){
        viewModelScope.launch {
            try {
                val response = loginUser(LoginUserRequest(email, password))

                if (response != null) {
                    preferencesManager.setSession(response.access_token, response.refresh_token)
                    result.postValue(Result.SUCCESS)
                } else {
                    result.postValue(Result.UNSUCCESS)
                }
            }
            catch (io: IOException) {
                result.postValue(Result.ERROR)
            }
        }
    }

    fun loginWithGoogle(token: String){
        viewModelScope.launch {
            try {
                val response = loginGoogle(LoginGoogleRequest(token))

                if (response != null) {
                    preferencesManager.setSession(response.access_token, response.refresh_token)
                    result.postValue(Result.SUCCESS)
                } else {
                    result.postValue(Result.UNSUCCESS)
                }
            }
            catch (io: IOException) {
                result.postValue(Result.ERROR)
            }
        }
    }

}