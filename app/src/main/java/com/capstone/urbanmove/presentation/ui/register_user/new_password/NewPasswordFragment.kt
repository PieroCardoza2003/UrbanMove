package com.capstone.urbanmove.presentation.ui.register_user.new_password

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.capstone.urbanmove.databinding.FragmentNewPasswordBinding
import com.capstone.urbanmove.presentation.ui.register_user.RegisterViewModel


class NewPasswordFragment : Fragment() {

    private val viewModel: RegisterViewModel by activityViewModels() //viewModel compartido
    private var _binding: FragmentNewPasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewPasswordBinding.inflate(inflater, container, false)

        binding.buttomClose.setOnClickListener { requireActivity().finish() }

        return binding.root
    }

}