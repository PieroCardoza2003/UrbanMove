package com.capstone.urbanmove.presentation.ui.dateinformation

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.capstone.urbanmove.R
import com.capstone.urbanmove.databinding.ActivityDateBinding
import java.util.*

class activity_date : AppCompatActivity() {

    private lateinit var binding: ActivityDateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.etFechaNacimiento.setOnClickListener {
            showDatePickerDialog(binding.etFechaNacimiento)
        }

        binding.ivToggleContrasena.setOnClickListener {
            togglePasswordVisibility(binding.etContrasena, binding.ivToggleContrasena)
        }

        binding.ivToggleConfirmarContrasena.setOnClickListener {
            togglePasswordVisibility(binding.etConfirmarContrasena, binding.ivToggleConfirmarContrasena)
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

    private fun togglePasswordVisibility(editText: EditText, imageView: ImageView) {
        if (editText.inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            imageView.setImageResource(R.drawable.ic_visibility_off)
        } else {
            editText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            imageView.setImageResource(R.drawable.ic_visibility)
        }
        editText.setSelection(editText.text.length)
    }
}
