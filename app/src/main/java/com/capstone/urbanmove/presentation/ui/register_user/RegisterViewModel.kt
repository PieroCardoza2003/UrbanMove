package com.capstone.urbanmove.presentation.ui.register_user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.urbanmove.data.remote.models.RegisterUserRequest
import com.capstone.urbanmove.data.remote.models.VerifyAccountRequest
import com.capstone.urbanmove.data.remote.models.VerifyCodeRequest
import com.capstone.urbanmove.domain.entity.Result
import com.capstone.urbanmove.domain.usecase.RegisterUserUseCase
import com.capstone.urbanmove.domain.usecase.VerifyAccountUserUseCase
import com.capstone.urbanmove.domain.usecase.VerifyCodeUserUseCase
import kotlinx.coroutines.launch
import java.io.IOException

class RegisterViewModel: ViewModel() {

    var currentUser: RegisterUserRequest? = null

    var verifyAccount = VerifyAccountUserUseCase()
    var verifyCode = VerifyCodeUserUseCase()
    var registerdata = RegisterUserUseCase()

    val resultVerifyAccount = MutableLiveData<Result>()
    val resultVerifyCode = MutableLiveData<Result>()
    val resultRegister = MutableLiveData<Result>()

    private fun registerUser() {
        viewModelScope.launch {
            try {
                val response = registerdata(RegisterUserRequest(
                    nombres = currentUser!!.nombres,
                    apellidos = currentUser!!.apellidos,
                    fecha_nacimiento = currentUser!!.fecha_nacimiento,
                    email = currentUser!!.email,
                    password = currentUser!!.password
                ))

                if (response != null){
                    resultRegister.postValue(Result.SUCCESS)
                } else {
                    resultRegister.postValue(Result.UNSUCCESS)
                }
            } catch (io: Exception) {
                resultRegister.postValue(Result.ERROR)
            }
        }
    }

    fun entryPassword(password: String){
        currentUser = currentUser!!.copy(password = password)
        registerUser()
    }

    fun entryVerificationCode(code:String) {
        viewModelScope.launch {
            try {
                val response = verifyCode(VerifyCodeRequest(currentUser!!.email, code))

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

    fun sendDataUser(nombre:String, apellidos: String?, email: String, fechanacimiento: String?) {
        viewModelScope.launch {
            try {
                currentUser = RegisterUserRequest(nombre, apellidos, fechanacimiento, email, null)
                val response = verifyAccount(VerifyAccountRequest(currentUser!!.email, type = "FIND_REGISTER"))

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

    fun resendDataUser() {
        viewModelScope.launch {
            try {
                currentUser = RegisterUserRequest(currentUser!!.nombres, currentUser!!.apellidos, currentUser!!.fecha_nacimiento, currentUser!!.email, null)
                val response = verifyAccount(VerifyAccountRequest(currentUser!!.email, type = "FIND_REGISTER"))

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
}