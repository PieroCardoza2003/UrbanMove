package com.capstone.urbanmove.presentation.ui.home_user.pasajero

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.urbanmove.MainApplication.Companion.preferencesManager
import com.capstone.urbanmove.data.remote.models.UsuarioDataResponse
import com.capstone.urbanmove.domain.entity.Result
import com.capstone.urbanmove.domain.usecase.GetDataUserUseCase
import com.capstone.urbanmove.utils.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.io.IOException

class PassengerViewModel: ViewModel() {

    var getdatauser = GetDataUserUseCase()
    val user = MutableLiveData<UsuarioDataResponse>()

    val result = MutableLiveData<Result>()



    private val client = OkHttpClient()
    private var webSocket: WebSocket? = null

    private val _isConnected = MutableStateFlow(false)
    val isConnected = _isConnected.asStateFlow()

    fun connect() {
        val accessToken = preferencesManager.getSession()?.access_token ?: ""
        val ws_url = "${Constants.getBaseWsUrbanmove()}/${accessToken}"

        if (webSocket == null) {
            val request = Request.Builder().url(ws_url).build()
            webSocket = client.newWebSocket(request, object : WebSocketListener() {
                override fun onOpen(webSocket: WebSocket, response: Response) {
                    viewModelScope.launch {
                        Log.d("prints", "ON OPEN")
                        _isConnected.value = true
                    }
                }

                override fun onMessage(webSocket: WebSocket, text: String) {
                    viewModelScope.launch {
                        Log.d("prints", "ON MESSAGE")
                    }
                }

                override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                    viewModelScope.launch {
                        Log.d("prints", "ON CLOSING")
                        _isConnected.value = false
                    }
                }

                override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                    viewModelScope.launch {
                        Log.d("prints", "ON CLOSED")
                        _isConnected.value = false
                    }
                }

                override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                    viewModelScope.launch {
                        Log.d("prints", "ON FAILURE")
                        _isConnected.value = false
                    }
                }
            })
        }
    }

    fun send(message: String) {
        webSocket?.send(message)
    }

    fun disconnect() {
        webSocket?.close(1000, "Cierre solicitado")
        webSocket = null
        viewModelScope.launch {
            Log.d("prints", "funcion disconect")
            _isConnected.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        disconnect() // Asegúrate de liberar la conexión cuando se destruya el ViewModel
    }


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