package com.capstone.urbanmove.presentation.ui.home_user.conductor

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.urbanmove.MainApplication.Companion.preferencesManager
import com.capstone.urbanmove.data.remote.models.ConductorEmpresaRequest
import com.capstone.urbanmove.data.remote.models.VerifyDriverAccountResponse
import com.capstone.urbanmove.domain.entity.Result
import com.capstone.urbanmove.domain.usecase.ListRegisterVehicle
import com.capstone.urbanmove.domain.usecase.RegistarConductorEmpresa
import com.capstone.urbanmove.domain.usecase.VerifyAccountDriverUseCase
import com.capstone.urbanmove.presentation.ui.home_user.conductor.registerDriver.privado.vehicle.objects.VehicleColor
import com.capstone.urbanmove.presentation.ui.home_user.conductor.registerDriver.privado.vehicle.objects.VehicleMarca
import com.capstone.urbanmove.presentation.ui.home_user.conductor.registerDriver.privado.vehicle.objects.VehicleModelo
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.IOException

class DriverViewModel: ViewModel() {

    val result = MutableLiveData<Result>()
    val result_conductor_empresa = MutableLiveData<Result>()
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
    val resultconductorEmpresa = MutableLiveData<Result>()



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
    /*
    Log.d("Prints", "\nDatos personales:" +
    "\nNombres: ${viewModel.c_nombres}" +
    "\nApellidos: ${viewModel.c_apellidos}" +
    "\nfecha_nacimiento: ${viewModel.c_fecha_nacimiento}" +
    "\nfoto_perfil: ${viewModel.c_foto_perfil}" +
    "\n\nDatos conductor:" +
    "\nnumero_licencia: ${viewModel.c_numero_licencia}" +
    "\nfecha_vencimiento: ${viewModel.c_fecha_vencimiento}" +
    "\nfoto_frontal: ${viewModel.c_licencia_frontal}" +
    "\nfoto_reverso: ${viewModel.c_licencia_reverso}" +
    "\n\nDatos Vehiculo:" +
    "\nplaca: ${viewModel.v_placa}" +
    "\nmarca: ${viewModel.v_marca.value}" +
    "\nmodelo: ${viewModel.v_modelo.value}" +
    "\ncolor: ${viewModel.v_color.value}")

     */

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

            } catch (io: Exception){

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