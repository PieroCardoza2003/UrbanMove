package com.capstone.urbanmove.presentation.ui.forgot_password_user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.urbanmove.data.remote.models.NewPasswordRequest
import com.capstone.urbanmove.data.remote.models.VerifyAccountRequest
import com.capstone.urbanmove.data.remote.models.VerifyCodeRequest
import com.capstone.urbanmove.domain.entity.Result
import com.capstone.urbanmove.domain.usecase.NewPasswordUseCase
import com.capstone.urbanmove.domain.usecase.VerifyAccountUserUseCase
import com.capstone.urbanmove.domain.usecase.VerifyCodeUserUseCase
import kotlinx.coroutines.launch
import java.io.IOException

class ForgotPasswordViewModel: ViewModel() {

    var verifyAccount = VerifyAccountUserUseCase()
    var verifyCode = VerifyCodeUserUseCase()
    var newPassword = NewPasswordUseCase()

    val resultPassword = MutableLiveData<Result>()
    val resultVerifyCode = MutableLiveData<Result>()
    val resultVerifyAccount = MutableLiveData<Result>()

    var currentEmail:String? = null

    fun entryPassword(password: String){
        viewModelScope.launch {
            try {
                val response = newPassword(NewPasswordRequest(currentEmail!!, password))

                if(response != null) {
                    resultPassword.postValue(Result.SUCCESS)
                } else {
                    resultPassword.postValue(Result.UNSUCCESS)
                }
            } catch (io: IOException) {
                resultPassword.postValue(Result.ERROR)
            }
        }
    }

    fun entryVerificationCode(code:String) {
        viewModelScope.launch {
            try {
                val response = verifyCode(VerifyCodeRequest(currentEmail!!, code))

                if(response != null) {
                    resultVerifyCode.postValue(Result.SUCCESS)
                } else {
                    resultVerifyCode.postValue(Result.UNSUCCESS)
                }
            } catch (io: IOException) {
                resultVerifyCode.postValue(Result.ERROR)
            }
        }
    }

    fun resendDataUser() {
        viewModelScope.launch {
            try {
                val response = verifyAccount(VerifyAccountRequest(currentEmail!!, type = "FIND_RECOVERY"))

                if(response != null) {
                    resultVerifyAccount.postValue(Result.SUCCESS)
                } else {
                    resultVerifyAccount.postValue(Result.UNSUCCESS)
                }
            } catch (io: IOException) {
                resultVerifyAccount.postValue(Result.ERROR)
            }
        }
    }

    fun findAccount(email: String) {
        viewModelScope.launch {
            try {
                val response = verifyAccount(VerifyAccountRequest(email, type = "FIND_RECOVERY"))

                if(response != null) {
                    currentEmail = email
                    resultVerifyAccount.postValue(Result.SUCCESS)
                } else {
                    resultVerifyAccount.postValue(Result.UNSUCCESS)
                }
            } catch (io: IOException) {
                resultVerifyAccount.postValue(Result.ERROR)
            }
        }
    }
}