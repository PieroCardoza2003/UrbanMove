package com.capstone.urbanmove.presentation.ui.home_user.conductor

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.urbanmove.MainApplication.Companion.preferencesManager
import com.capstone.urbanmove.data.remote.models.ConductorEmpresaRequest
import com.capstone.urbanmove.data.remote.models.ConductorPrivadoRequest
import com.capstone.urbanmove.data.remote.models.VerifyDriverAccountResponse
import com.capstone.urbanmove.domain.entity.Result
import com.capstone.urbanmove.domain.usecase.GetRutasUseCase
import com.capstone.urbanmove.domain.usecase.ListRegisterVehicle
import com.capstone.urbanmove.domain.usecase.RegistarConductorEmpresa
import com.capstone.urbanmove.domain.usecase.RegisterConductorPrivado
import com.capstone.urbanmove.domain.usecase.VerifyAccountDriverUseCase
import com.capstone.urbanmove.presentation.ui.home_user.conductor.registerDriver.privado.vehicle.objects.VehicleColor
import com.capstone.urbanmove.presentation.ui.home_user.conductor.registerDriver.privado.vehicle.objects.VehicleMarca
import com.capstone.urbanmove.presentation.ui.home_user.conductor.registerDriver.privado.vehicle.objects.VehicleModelo
import com.capstone.urbanmove.presentation.ui.home_user.pasajero.mapview.bottomsheet.models.Ruta
import com.capstone.urbanmove.utils.Constants
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.JsonParser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.io.File
import java.io.IOException

class DriverViewModel: ViewModel() {

    val result = MutableLiveData<Result>()
    var getrutas = GetRutasUseCase()
    val listaRutas = MutableLiveData<List<Ruta>>()

    val detectado = MutableLiveData<Detectado>()

    var trayectoria: String = "Ida"


    val result_conductor_empresa = MutableLiveData<Result>()
    val result_conductor_privado = MutableLiveData<Result>()

    val marcas_result = MutableLiveData<List<VehicleMarca>>()
    val modelos_result = MutableLiveData<List<VehicleModelo>>()
    val colores_result = MutableLiveData<List<VehicleColor>>()

    var verifyAccountDriver = VerifyAccountDriverUseCase()
    var accountDriver: VerifyDriverAccountResponse? = null
    var driver_data = MutableLiveData<VerifyDriverAccountResponse>()

    var dataVehiculos = ListRegisterVehicle()

    var c_tipo_conductor: String = ""

    var c_nombres: String = ""
    var c_apellidos: String = ""
    var c_fecha_nacimiento: String = ""
    var c_foto_perfil: String = ""
    var c_foto_perfil_type: String = ""

    var c_licencia_frontal: String = ""
    var c_licencia_reverso: String = ""
    var c_numero_licencia: String = ""
    var c_fecha_vencimiento: String = ""

    var v_placa: String = ""
    val v_marca = MutableLiveData<VehicleMarca>()
    val v_modelo = MutableLiveData<VehicleModelo>()
    val v_color = MutableLiveData<VehicleColor>()

    var createConductorEmpresa = RegistarConductorEmpresa()
    var createConductorPrivado = RegisterConductorPrivado()

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
                        viewModelScope.launch {
                            try{
                                val jsonObject = JsonParser.parseString(text).asJsonObject

                                val longitud = jsonObject.get("longitud").asDouble
                                val latitud = jsonObject.get("latitud").asDouble
                                val id_pasajero = jsonObject.get("id_pasajero").asString

                                val message = Detectado(
                                    id_usuario = id_pasajero,
                                    location = LatLng(latitud, longitud)
                                )

                                detectado.postValue(message)
                                Log.d("prints", "el mensaje recibido es: $message")
                            } catch (io: IOException){
                                Log.d("prints", "Error en en mensaje ws")
                            }
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

    data class ConductorMessage(
        val id_conductor: String,
        val id_ruta: Int,
        val trayectoria: String,
        val rango: Double,
        val longitud: Double,
        val latitud: Double,
        val type: String
    )

    fun send(location: LatLng) {
        val gson = Gson()
        val conductorMessage = ConductorMessage(
            id_conductor = accountDriver!!.id_conductor ?: "",
            id_ruta = 11,
            trayectoria = trayectoria,
            rango = 500.0,
            longitud = location.longitude,
            latitud = location.latitude,
            type = "CONDUCTOR"
        )
        val jsonMessage = gson.toJson(conductorMessage)
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




    fun loaddata(){
        viewModelScope.launch {
            try {
                accountDriver = verifyAccountDriver()

                if (accountDriver == null ) {
                    result.postValue(Result.NONE)
                    return@launch
                }

                if (accountDriver!!.tipo_conductor == null || accountDriver!!.id_conductor == null) {
                    result.postValue(Result.UNSUCCESS)
                    return@launch
                }

                driver_data.postValue(accountDriver)
                result.postValue(Result.SUCCESS)
            }
            catch (io: IOException) {
                result.postValue(Result.ERROR)
            }
        }
    }

    fun registrar_conductor_empresa(codigo_empleado: String) {
        viewModelScope.launch {
            try {
                val conductor = ConductorEmpresaRequest(
                    id_usuario = accountDriver!!.id_usuario,
                    nombre = c_nombres,
                    apellido = c_apellidos,
                    fecha_nacimiento = c_fecha_nacimiento,
                    foto_perfil = if (c_foto_perfil_type == "URL") c_foto_perfil else null,
                    numero_licencia = c_numero_licencia,
                    fecha_vencimiento = c_fecha_vencimiento,
                    codigo_empleado = codigo_empleado
                )

                val foto_perfil_part: MultipartBody.Part?  = if (c_foto_perfil_type == "GALLERY") {
                    val file = File(c_foto_perfil)
                    val requestFile = RequestBody.create(MediaType.parse("image/*"), file)
                    MultipartBody.Part.createFormData("fotoperfil", file.name, requestFile)
                } else { null }

                val licencia_frontal_part: MultipartBody.Part = File(c_licencia_frontal).let { file ->
                    val requestBody = RequestBody.create(MediaType.parse("image/*"), file)
                    MultipartBody.Part.createFormData("licencia_frontal", file.name, requestBody)
                }

                val licencia_reverso_part: MultipartBody.Part = File(c_licencia_reverso).let { file ->
                    val requestBody = RequestBody.create(MediaType.parse("image/*"), file)
                    MultipartBody.Part.createFormData("licencia_reverso", file.name, requestBody)
                }

                val response = createConductorEmpresa(
                    conductor = conductor,
                    fotoperfil = foto_perfil_part,
                    licenciaFrontal = licencia_frontal_part,
                    licenciaReverso = licencia_reverso_part
                )

                if (response != null){
                    result_conductor_empresa.postValue(Result.SUCCESS)
                    return@launch
                } else {
                    result_conductor_empresa.postValue(Result.UNSUCCESS)
                }
            } catch (io: Exception) {
                result_conductor_empresa.postValue(Result.ERROR)
            }
        }
    }


    fun registrar_conductor_privado() {
        viewModelScope.launch {
            try {
                val conductor = ConductorPrivadoRequest(
                    id_usuario = accountDriver!!.id_usuario,
                    nombre = c_nombres,
                    apellido = c_apellidos,
                    fecha_nacimiento = c_fecha_nacimiento,
                    foto_perfil = if (c_foto_perfil_type == "URL") c_foto_perfil else null,
                    numero_licencia = c_numero_licencia,
                    fecha_vencimiento = c_fecha_vencimiento,
                    numero_placa = v_placa,
                    marca_vehiculo = v_marca.value!!.marca,
                    modelo_vehiculo = v_modelo.value!!.modelo,
                    color_vehiculo = v_color.value!!.color
                )

                val foto_perfil_part: MultipartBody.Part?  = if (c_foto_perfil_type == "GALLERY") {
                    val file = File(c_foto_perfil)
                    val requestFile = RequestBody.create(MediaType.parse("image/*"), file)
                    MultipartBody.Part.createFormData("fotoperfil", file.name, requestFile)
                } else { null }

                val licencia_frontal_part: MultipartBody.Part = File(c_licencia_frontal).let { file ->
                    val requestBody = RequestBody.create(MediaType.parse("image/*"), file)
                    MultipartBody.Part.createFormData("licencia_frontal", file.name, requestBody)
                }

                val licencia_reverso_part: MultipartBody.Part = File(c_licencia_reverso).let { file ->
                    val requestBody = RequestBody.create(MediaType.parse("image/*"), file)
                    MultipartBody.Part.createFormData("licencia_reverso", file.name, requestBody)
                }

                val response = createConductorPrivado(
                    conductor = conductor,
                    fotoperfil = foto_perfil_part,
                    licenciaFrontal = licencia_frontal_part,
                    licenciaReverso = licencia_reverso_part
                )

                if (response != null){
                    result_conductor_privado.postValue(Result.SUCCESS)
                    return@launch
                } else {
                    result_conductor_privado.postValue(Result.UNSUCCESS)
                }
            } catch (io: Exception){
                result_conductor_privado.postValue(Result.ERROR)
            }
        }
    }


    fun fetchRutasPrivado(){
        viewModelScope.launch {
            try {
                val rutas = getrutas.rutas_conductor_privado()

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

    fun fetchMarcas(){
        viewModelScope.launch {
            try {
                val marcas = dataVehiculos.getListaMarcas()

                if (marcas.isNotEmpty()){
                    marcas_result.postValue(marcas)
                    return@launch
                }
                marcas_result.postValue(emptyList())
            } catch (io: IOException) {
                marcas_result.postValue(emptyList())
            }
        }
    }

    fun fetchModelos(){
        viewModelScope.launch {
            try {
                val modelos = dataVehiculos.getListaModelos(v_marca.value!!.id_marca)

                if (modelos.isNotEmpty()){
                    modelos_result.postValue(modelos)
                    return@launch
                }
                modelos_result.postValue(emptyList())
            } catch (io: IOException) {
                modelos_result.postValue(emptyList())
            }
        }
    }

    fun fetchColores(){
        viewModelScope.launch {
            try {
                val colores = dataVehiculos.getListaColores()

                if (colores.isNotEmpty()){
                    colores_result.postValue(colores)
                    return@launch
                }
                colores_result.postValue(emptyList())
            } catch (io: IOException) {
                colores_result.postValue(emptyList())
            }
        }
    }


    fun closeSession(){
        preferencesManager.removeSession()
    }



}