package com.capstone.urbanmove.presentation.ui.dateaccount

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.urbanmove.domain.entity.UserBody
import com.capstone.urbanmove.domain.usecase.CreateUser
import kotlinx.coroutines.launch
import java.io.IOException

class RegisterViewModel: ViewModel() {

    val result = MutableLiveData<String>()
    val userUseCase = CreateUser()

    fun createUser(user: UserBody, pass: String){
        viewModelScope.launch {

            if (user.nombres.isBlank() ||
                user.correo.isBlank() ||
                user.contrasena.isBlank() ||
                pass.isBlank()){
                result.postValue("Complete todos los campos requeridos")
                return@launch
            }

            if (user.contrasena != pass){
                result.postValue("Las contraseñas no coinciden")
                return@launch
            }

            try {
                val response = userUseCase(user)

                if(response != null){
                    result.postValue("Registro exitoso")
                    return@launch
                }
                result.postValue("El usuario ya esta registrado")

            } catch (ex: IOException){
                Log.d("prints", "error: ${ex.message}")
                result.postValue("Ocurrio un error en el servidor")
            }

        }

    }

}