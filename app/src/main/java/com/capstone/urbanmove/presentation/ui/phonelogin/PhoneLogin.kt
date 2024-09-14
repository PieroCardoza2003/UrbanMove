package com.capstone.urbanmove.presentation.ui.phonelogin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.urbanmove.databinding.ActivityPhoneLoginBinding
import com.capstone.urbanmove.presentation.ui.otpverification.OtpVerification
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import java.util.concurrent.TimeUnit

class PhoneLogin : AppCompatActivity() {
    private lateinit var binding: ActivityPhoneLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var verificationId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhoneLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Inicializar FirebaseAuth
        auth = FirebaseAuth.getInstance()

        //Country Code Picker
        binding.countryPicker.setOnCountryChangeListener {
            val country_code = "+" + binding.countryPicker.selectedCountryCode
            binding.countryCode.setText(country_code)
        }

        //Capturar_telefono
        binding.btngetOTP.setOnClickListener {
            val teléfono = binding.telFono.text.toString().trim()

            if (teléfono.isEmpty()){
                Toast.makeText(this, "Ingresa tú teléfono",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            binding.progressBar.visibility = View.VISIBLE
            binding.btngetOTP.visibility = View.INVISIBLE



        }
    }

    //Enviar codigo de verificación
    private fun sendVerificationCode(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber) //número
            .setTimeout(60L, TimeUnit.SECONDS) //tiempo de espera
            .setActivity(this)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    // Caso de verificación automatica
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.btngetOTP.visibility = View.VISIBLE
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    // caso de fallo
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.btngetOTP.visibility = View.VISIBLE
                    Toast.makeText(this@PhoneLogin, e.message, Toast.LENGTH_LONG).show()
                }

                override fun onCodeSent(verficiationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(verificationId, token)
                    this@PhoneLogin.verificationId = verificationId
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.btngetOTP.visibility = View.VISIBLE

                    // Redirigimos a la pantalla de verificación OTP
                    val intent = Intent(this@PhoneLogin, OtpVerification::class.java)
                    intent.putExtra("verificationId", verificationId)
                    intent.putExtra("teléfono", phoneNumber)
                    startActivity(intent)
                }
            }).build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
}