package com.capstone.urbanmove.presentation.ui.forgot_password_user.verify_code

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.capstone.urbanmove.R
import com.capstone.urbanmove.databinding.FragmentNewPasswordBinding
import com.capstone.urbanmove.databinding.FragmentVerifyCodeBinding
import com.capstone.urbanmove.presentation.ui.forgot_password_user.ForgotPasswordViewModel

class VerifyCodeFragment : Fragment() {

    private val viewModel: ForgotPasswordViewModel by activityViewModels() //viewModel compartido
    private var _binding: FragmentVerifyCodeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVerifyCodeBinding.inflate(inflater, container, false)

        return binding.root
    }

}