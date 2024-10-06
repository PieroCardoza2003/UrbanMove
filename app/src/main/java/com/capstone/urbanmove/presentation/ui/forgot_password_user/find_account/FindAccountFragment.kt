package com.capstone.urbanmove.presentation.ui.forgot_password_user.find_account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.capstone.urbanmove.R
import com.capstone.urbanmove.databinding.FragmentCreateAccountBinding
import com.capstone.urbanmove.databinding.FragmentFindAccountBinding
import com.capstone.urbanmove.presentation.ui.forgot_password_user.ForgotPasswordViewModel
import com.capstone.urbanmove.presentation.ui.register_user.RegisterViewModel


class FindAccountFragment : Fragment() {

    private val viewModel: ForgotPasswordViewModel by activityViewModels() //viewModel compartido
    private var _binding: FragmentFindAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFindAccountBinding.inflate(inflater, container, false)

        return binding.root
    }

}