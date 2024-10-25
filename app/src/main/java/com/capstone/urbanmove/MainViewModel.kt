package com.capstone.urbanmove

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.urbanmove.MainApplication.Companion.preferencesManager
import com.capstone.urbanmove.domain.usecase.VerifySessionUserUseCase
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel: ViewModel() {

    private val _isLogged = MutableLiveData<String>() // Modificable
    val isLogged: LiveData<String> = _isLogged // Observable
    var getSessionUser = VerifySessionUserUseCase()

    private fun setIsLogged(state: String){
        _isLogged.value = state
    }

    fun checkIsLogged(){
        viewModelScope.launch {
            try {
                val session = preferencesManager.getSession()
                if (session != null) {
                    val response = getSessionUser()
                    if (response != null){
                        when (response.rol_actual) {
                            "PASAJERO" -> setIsLogged("PASAJERO")
                            "CONDUCTOR" -> setIsLogged("CONDUCTOR")
                            else -> setIsLogged("NONE")
                        }
                    } else {
                        setIsLogged("NONE")
                    }
                } else {
                    setIsLogged("NONE")
                }
            } catch (io: IOException){
                setIsLogged("NONE")
            }
        }
    }

}