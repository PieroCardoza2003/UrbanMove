package com.capstone.urbanmove.presentation.ui.dateaccount

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.capstone.urbanmove.R
import com.capstone.urbanmove.databinding.ActivityDateUserBinding
import android.app.DatePickerDialog
import android.content.Intent
import android.text.InputType
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.capstone.urbanmove.data.remote.user.UserService
import com.capstone.urbanmove.domain.entity.UserBody
import com.capstone.urbanmove.presentation.ui.otpverification.OtpVerification
import kotlinx.coroutines.launch
import java.util.Calendar

class DateUser : AppCompatActivity() {
    private lateinit var binding: ActivityDateUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDateUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val messagecrea = "Para activar su cuenta introduzca el código que enviamos a"

        binding.etFechaNacimiento.setOnClickListener{
            showDatePickerDialog(binding.etFechaNacimiento)
        }

        binding.btnsavedata.setOnClickListener {
            val nombres = binding.etname.text.toString()
            val apellido = binding.etsurname.text.toString()
            val email = binding.etmail.text.toString()
            val fechanac = binding.etFechaNacimiento.text.toString()
            val password = binding.etpassword.text.toString()
            val conpassword = binding.etconfirmpassword.text.toString()
            if (nombres.isBlank() || email.isBlank() || password.isBlank() || conpassword.isBlank()){
                Toast.makeText(this, "Complete los campos requeridos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password != conpassword){
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            val user = UserBody(nombres,apellido,fechanac,email,password)
            lifecycleScope.launch {
                val response = UserService().createUser(user)
                if (response != null) {
                    Toast.makeText(this@DateUser, "Usuario Creado", Toast.LENGTH_SHORT).show()
                    Log.d("prints","$response")
                } else {
                    Toast.makeText(this@DateUser, "Ocurrio un error", Toast.LENGTH_SHORT).show()
                    Log.d("prints","error")
                }
            }



            /*//Capturar email
            val email = binding.etmail.text.toString()

            val intent = Intent(this,OtpVerification::class.java).apply {
                putExtra("EMAIL_KEY",email)
                putExtra("MSG",messagecrea)
            }
            startActivity(intent)*/
        }
    }
    private fun showDatePickerDialog(editText: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                editText.setText(formattedDate)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }
}