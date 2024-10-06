package com.capstone.urbanmove.presentation.ui.register_user.verify_code

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.capstone.urbanmove.databinding.FragmentVerifyCodeBinding
import com.capstone.urbanmove.presentation.ui.register_user.RegisterViewModel

class VerifyCodeFragment : Fragment() {

    private val viewModel: RegisterViewModel by activityViewModels() //viewModel compartido
    private var _binding: FragmentVerifyCodeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVerifyCodeBinding.inflate(inflater, container, false)

        binding.buttomClose.setOnClickListener { requireActivity().finish() }

        return binding.root
    }

}