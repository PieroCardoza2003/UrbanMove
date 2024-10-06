package com.capstone.urbanmove.presentation.ui.register_user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.urbanmove.data.remote.models.RegisterUserRequest
import kotlinx.coroutines.launch

class RegisterViewModel: ViewModel() {

    private var currentUser: RegisterUserRequest? = null

    fun registerUser(){
        viewModelScope.launch {
            try {
                val response = currentUser?.let {
                    RegisterUserRequest(
                        it.nombres,
                        it.apellido,
                        it.fecha_nacimiento,
                        it.email,
                        it.password
                    )
                }

            } catch (io: Exception){

            }
        }
    }

    fun sendDataUser(nombre:String, apellidos: String?, email: String, fechanacimiento: String?){
        currentUser = RegisterUserRequest(nombre, apellidos, fechanacimiento, email, "")
    }

}