package com.capstone.urbanmove.data.remote.user

import com.capstone.urbanmove.data.remote.models.LoginGoogleRequest
import com.capstone.urbanmove.data.remote.models.LoginResponse
import com.capstone.urbanmove.data.remote.models.LoginUserRequest
import com.capstone.urbanmove.data.remote.models.NewPasswordRequest
import com.capstone.urbanmove.data.remote.models.RegisterUserRequest
import com.capstone.urbanmove.data.remote.models.VerifyAccountRequest
import com.capstone.urbanmove.data.remote.models.VerifyCodeRequest
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface UserApiClient {

    @POST("/api/user/register")
    suspend fun createUser(@Body request: RegisterUserRequest): Response<*>

    @POST("/api/user/new-password")
    suspend fun newPasswordUser(@Body request: NewPasswordRequest): Response<*>

    @POST("/api/user/login")
    suspend fun loginUser(@Body request: LoginUserRequest): Response<LoginResponse>

    @POST("/api/user/login-google")
    suspend fun loginGoogle(@Body request: LoginGoogleRequest): Response<LoginResponse>

    @POST("/api/user/verify-account")
    suspend fun verifyAccount(@Body request: VerifyAccountRequest): Response<*>

    @POST("/api/user/verify-code")
    suspend fun verifyCode(@Body request: VerifyCodeRequest): Response<*>

    @Multipart
    @POST("/api/driver/register-company")
    suspend fun createConductorEmpresa(
        @Query("id_usuario") id_usuario: String,
        @Query("nombre") nombre: String,
        @Query("apellido") apellido: String,
        @Query("fecha_nacimiento") fecha_nacimiento: String,
        @Query("foto_perfil") foto_perfil: String?,
        @Query("numero_licencia") numero_licencia: String,
        @Query("fecha_vencimiento") fecha_vencimiento: String,
        @Query("codigo_empleado") codigo_empleado: String,

        @Part fotoperfil: MultipartBody.Part?,
        @Part licencia_frontal: MultipartBody.Part,
        @Part licencia_reverso: MultipartBody.Part
    ): Response<*>

    @Multipart
    @POST("/api/driver/register-private")
    suspend fun createConductorPrivado(
        @Query("id_usuario") id_usuario: String,
        @Query("nombre") nombre: String,
        @Query("apellido") apellido: String,
        @Query("fecha_nacimiento") fecha_nacimiento: String,
        @Query("foto_perfil") foto_perfil: String?,
        @Query("numero_licencia") numero_licencia: String,
        @Query("fecha_vencimiento") fecha_vencimiento: String,
        @Query("numero_placa") numero_placa: String,
        @Query("marca_vehiculo") marca_vehiculo: String,
        @Query("modelo_vehiculo") modelo_vehiculo: String,
        @Query("color_vehiculo") color_vehiculo: String,

        @Part fotoperfil: MultipartBody.Part?,
        @Part licencia_frontal: MultipartBody.Part,
        @Part licencia_reverso: MultipartBody.Part
    ): Response<*>

}
