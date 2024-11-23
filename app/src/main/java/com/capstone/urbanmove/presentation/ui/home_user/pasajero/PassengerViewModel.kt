package com.capstone.urbanmove.presentation.ui.home_user.pasajero

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.urbanmove.MainApplication.Companion.preferencesManager
import com.capstone.urbanmove.data.remote.models.UsuarioDataResponse
import com.capstone.urbanmove.domain.entity.Result
import com.capstone.urbanmove.domain.usecase.GetDataUserUseCase
import kotlinx.coroutines.launch
import java.io.IOException

class PassengerViewModel: ViewModel() {

    var getdatauser = GetDataUserUseCase()
    val user = MutableLiveData<UsuarioDataResponse>()

    val result = MutableLiveData<Result>()

    fun loaddata() {
        viewModelScope.launch {
            try {
                val response = getdatauser()

                if (response != null) {
                    user.postValue(response)
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


    fun closeSession(){
        preferencesManager.removeSession()
    }

}