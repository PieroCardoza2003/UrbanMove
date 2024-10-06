package com.capstone.urbanmove.presentation.ui.register_user.create_account

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import com.capstone.urbanmove.databinding.FragmentCreateAccountBinding
import com.capstone.urbanmove.presentation.ui.register_user.RegisterViewModel
import com.capstone.urbanmove.utils.VerifyEmail
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CreateAccountFragment : Fragment() {

    private val viewModel: RegisterViewModel by activityViewModels() //viewModel compartido
    private var _binding: FragmentCreateAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateAccountBinding.inflate(inflater, container, false)


        binding.btnsavedata.setOnClickListener {
            val nombre = binding.etname.text.toString()
            val apellidos = if (binding.etsurname.text.isNullOrEmpty()) null else binding.etsurname.text.toString()
            val email = binding.etmail.text.toString()
            val fechanacimiento = if(binding.etFechaNacimiento.text.isNullOrEmpty()) null else binding.etFechaNacimiento.text.toString()

            if (nombre.isBlank()){
                binding.layoutName.error = "Campo obligatorio"
                return@setOnClickListener
            }
            if (email.isBlank()){
                binding.layoutEmail.error = "Campo obligatorio"
                return@setOnClickListener
            }
            if (VerifyEmail.isValid(email)){
                binding.layoutEmail.error = "El email no es valido"
                return@setOnClickListener
            }

            viewModel.sendDataUser(nombre, apellidos, email, fechanacimiento)
        }

        binding.etFechaNacimiento.setOnClickListener{ showDatePickerDialog(binding.etFechaNacimiento) }

        binding.buttomClose.setOnClickListener { requireActivity().finish() }

        return binding.root
    }

    private fun showDatePickerDialog(editText: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->

                val selectedDate = Calendar.getInstance()
                selectedDate.set(selectedYear, selectedMonth, selectedDay)

                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedDate.time)
                editText.setText(formattedDate)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

}