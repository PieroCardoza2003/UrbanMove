package com.capstone.urbanmove.presentation.ui.forgot_password_user.find_account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.capstone.urbanmove.R
import com.capstone.urbanmove.databinding.FragmentCreateAccountBinding
import com.capstone.urbanmove.databinding.FragmentFindAccountBinding
import com.capstone.urbanmove.domain.entity.Result
import com.capstone.urbanmove.presentation.ui.common.LoadDialogFragment
import com.capstone.urbanmove.presentation.ui.forgot_password_user.ForgotPasswordViewModel
import com.capstone.urbanmove.presentation.ui.register_user.RegisterViewModel
import com.capstone.urbanmove.utils.VerifyEmail


class FindAccountFragment : Fragment() {

    private val viewModel: ForgotPasswordViewModel by activityViewModels() //viewModel compartido
    private var _binding: FragmentFindAccountBinding? = null
    private val binding get() = _binding!!
    private var loadDialogFragment: LoadDialogFragment? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFindAccountBinding.inflate(inflater, container, false)

        binding.buttomClose.setOnClickListener { requireActivity().finish() }

        viewModel.resultVerifyAccount.observe(viewLifecycleOwner){ result ->
            showLoadAnimation(false)
            when(result){
                Result.SUCCESS -> findNavController().navigate(R.id.action_verify_code_forgotpassword)
                Result.UNSUCCESS -> binding.layoutemail.error = "Esta cuenta no esta registrada"
                else -> Toast.makeText(requireContext(), "Internal error", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnverificar.setOnClickListener {
            val email = binding.emailreco.text.toString()

            if(email.isBlank() || !VerifyEmail.isValid(email)){
                binding.layoutemail.error = "complete el campo correctamente"
                return@setOnClickListener
            }
            showLoadAnimation(true)
            viewModel.findAccount(email)
        }

        return binding.root
    }

    private fun showLoadAnimation(state: Boolean) {
        if (state) {
            if (loadDialogFragment == null) {
                loadDialogFragment = LoadDialogFragment("Cargando")
                loadDialogFragment?.show(childFragmentManager, "load_dialog")
            }
        } else {
            loadDialogFragment?.dismiss()
            loadDialogFragment = null
        }
    }

}