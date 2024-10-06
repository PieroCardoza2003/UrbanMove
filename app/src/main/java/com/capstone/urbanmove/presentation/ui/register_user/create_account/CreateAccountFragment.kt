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
import java.util.Calendar

class CreateAccountFragment : Fragment() {

    private val viewModel: RegisterViewModel by activityViewModels() //viewModel compartido
    private var _binding: FragmentCreateAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateAccountBinding.inflate(inflater, container, false)


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