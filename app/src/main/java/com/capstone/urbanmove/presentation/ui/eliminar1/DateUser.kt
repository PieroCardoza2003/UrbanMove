package com.capstone.urbanmove.presentation.ui.eliminar1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class DateUser : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
        binding = ActivityDateUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etFechaNacimiento.setOnClickListener{
            showDatePickerDialog(binding.etFechaNacimiento)
        }

        binding.buttomClose.setOnClickListener {
            finish()
        }

        viewModel.result.observe(this){ result ->
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
            if(result=="Registro exitoso"){
                val i = Intent(this, NavOptions::class.java)
                startActivity(i)
                finish()
            }
        }


        binding.btnsavedata.setOnClickListener {
            val user = UserBody(
                nombres = binding.etname.text.toString(),
                apellido = if (binding.etsurname.text.isNullOrEmpty()) null else binding.etsurname.text.toString(),
                fechanacimiento = if (binding.etFechaNacimiento.text.isNullOrEmpty()) null else binding.etFechaNacimiento.text.toString(),
                correo = binding.etmail.text.toString(),
                contrasena = binding.etpassword.text.toString()
            )
            val pass = binding.etconfirmpassword.text.toString()

            viewModel.createUser(user, pass)
        }
         */
    }
    /*
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

     */
}