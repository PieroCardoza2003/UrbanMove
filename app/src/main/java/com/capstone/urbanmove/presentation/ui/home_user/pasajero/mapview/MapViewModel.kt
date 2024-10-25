package com.capstone.urbanmove.presentation.ui.home_user.pasajero.mapview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MapViewModel: ViewModel() {

    val radius = MutableLiveData<Int>()

    fun setRadius(value: Int){
        radius.postValue(value)
    }

}