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
import android.widget.EditText
import android.widget.ImageView
import com.capstone.urbanmove.presentation.ui.otpverification.OtpVerification
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

            //Capturar email
            val email = binding.etmail.text.toString()

            val intent = Intent(this,OtpVerification::class.java).apply {
                putExtra("EMAIL_KEY",email)
                putExtra("MSG",messagecrea)
            }
            startActivity(intent)
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