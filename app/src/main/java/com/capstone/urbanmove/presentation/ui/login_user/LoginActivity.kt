package com.capstone.urbanmove.presentation.ui.login_user

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.urbanmove.R
import com.capstone.urbanmove.databinding.ActivityLoginBinding
import com.capstone.urbanmove.domain.entity.Result
import com.capstone.urbanmove.presentation.ui.forgot_password_user.ForgotPasswordActivity
import com.capstone.urbanmove.presentation.ui.register_user.RegisterActivity
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.IOException

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    private lateinit var auth: FirebaseAuth
    private var oneTapClient: SignInClient? = null
    private lateinit var signInRequest: BeginSignInRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        oneTapClient = Identity.getSignInClient(this)

        signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(getString(R.string.default_web_client_id))
                    .setFilterByAuthorizedAccounts(false)
                    .build())
            .build()

        binding.btnlogingoogle.setOnClickListener{
            CoroutineScope(Dispatchers.Main).launch {
                signingGoogle()
            }
        }

        binding.buttonLogin.setOnClickListener {
            val email = binding.edittextEmail.text.toString()
            val password = binding.edittextPassword.text.toString()

            if(email.isBlank()){
                binding.edittextEmailLayout.errorIconDrawable = null
                binding.edittextEmailLayout.error = "Este campo no puede estar vacío"
            }
            if (password.isBlank()){
                binding.edittextPasswordLayout.errorIconDrawable = null
                binding.edittextPasswordLayout.error = "Este campo no puede estar vacío"
            }
            viewModel.loginUser(email, password)
        }

        binding.forgotpassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        binding.register.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        viewModel.result.observe(this){ result ->
            when(result) {
                Result.SUCCESS ->{}
                Result.UNSUCCESS -> {
                    Toast.makeText(this, "Las credenciales no son validas", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(this, "Internal Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private suspend fun signingGoogle() {
        val result = oneTapClient?.beginSignIn(signInRequest)?.await()
        val intentSenderRequest = IntentSenderRequest.Builder(result!!.pendingIntent).build()
        activityResultLauncher.launch(intentSenderRequest)
    }


    private val activityResultLauncher: ActivityResultLauncher<IntentSenderRequest> =
        registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                try {
                    val credential = oneTapClient!!.getSignInCredentialFromIntent(result.data)
                    val idToken = credential.googleIdToken
                    if (idToken != null) {
                        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                        auth.signInWithCredential(firebaseCredential).addOnCompleteListener {
                            if (it.isSuccessful) {
                                viewModel.loginWithGoogle(idToken)
                            }
                        }
                    }
                } catch (e: IOException) {
                    Toast.makeText(this, "Ocurrió un error durante la autenticación", Toast.LENGTH_LONG).show()
                }
            }
        }
}