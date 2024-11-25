package com.capstone.urbanmove.presentation.ui.home_user.pasajero

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.urbanmove.MainApplication.Companion.preferencesManager
import com.capstone.urbanmove.data.remote.models.UsuarioDataResponse
import com.capstone.urbanmove.domain.entity.Result
import com.capstone.urbanmove.domain.usecase.GetDataUserUseCase
import com.capstone.urbanmove.domain.usecase.GetRutasUseCase
import com.capstone.urbanmove.presentation.ui.home_user.conductor.Detectado
import com.capstone.urbanmove.presentation.ui.home_user.pasajero.mapview.bottomsheet.models.Ruta
import com.capstone.urbanmove.utils.Constants
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.JsonParser
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
    var getrutas = GetRutasUseCase()
    val listaRutas = MutableLiveData<List<Ruta>>()

    val detectado = MutableLiveData<Detectado>()

    val user = MutableLiveData<UsuarioDataResponse>()

    val result = MutableLiveData<Result>()

    var selected_transporte: Int = -1
    var rutas_selected: MutableList<Ruta> = mutableListOf()
    var trayectoria: String = "Ida"

    fun add_ruta_list(ruta: Ruta){
        if (rutas_selected.size <= 2){
            rutas_selected.add(ruta)
            Log.d("prints", "se añadoio: $rutas_selected")
        }
    }

    fun cancelar_solicitud(){
        selected_transporte = -1
        rutas_selected = mutableListOf()
        disconnect()
    }



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
                        try{
                            val jsonObject = JsonParser.parseString(text).asJsonObject

                            val longitud = jsonObject.get("longitud").asDouble
                            val latitud = jsonObject.get("latitud").asDouble
                            val id_conductor = jsonObject.get("id_conductor").asString

                            val message = Detectado(
                                id_usuario = id_conductor,
                                location = LatLng(latitud, longitud)
                            )

                            detectado.postValue(message)
                            Log.d("prints", "el mensaje recibido es: $message")
                        } catch (io: IOException){
                            Log.d("prints", "Error en en mensaje ws")
                        }
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

    data class PasajeroMessage(
        val id_pasajero: String,
        val rutas: List<Ruta>,
        val trayectoria: String,
        val rango: Double,
        val longitud: Double,
        val latitud: Double,
        val type: String
    )

    fun send(location: LatLng) {
        val gson = Gson()
        val pasajeroMessage = PasajeroMessage(
            id_pasajero = user.value!!.id_usuario,
            rutas = rutas_selected,
            trayectoria = trayectoria,
            rango = 500.0,
            longitud = location.longitude,
            latitud = location.latitude,
            type = "PASAJERO"
        )
        val jsonMessage = gson.toJson(pasajeroMessage)
        Log.d("prints", "${jsonMessage}")
        webSocket?.send(jsonMessage)
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


    fun fetchRutas(){
        viewModelScope.launch {
            try {
                val rutas = getrutas.rutas_pasajero()

                if (rutas.isNotEmpty()){
                    listaRutas.postValue(rutas)
                    return@launch
                }
                listaRutas.postValue(emptyList())
            } catch (io: IOException) {
                listaRutas.postValue(emptyList())
            }
        }
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