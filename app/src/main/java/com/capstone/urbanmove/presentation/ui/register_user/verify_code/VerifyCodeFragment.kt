package com.capstone.urbanmove.presentation.ui.register_user.verify_code

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.capstone.urbanmove.R
import com.capstone.urbanmove.databinding.FragmentVerifyCodeBinding
import com.capstone.urbanmove.domain.entity.Result
import com.capstone.urbanmove.presentation.ui.common.ErrorActivity
import com.capstone.urbanmove.presentation.ui.common.LoadDialogFragment
import com.capstone.urbanmove.presentation.ui.register_user.RegisterViewModel

class VerifyCodeFragment : Fragment() {

    private val viewModel: RegisterViewModel by activityViewModels() //viewModel compartido
    private var _binding: FragmentVerifyCodeBinding? = null
    private val binding get() = _binding!!
    private var loadDialogFragment: LoadDialogFragment? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVerifyCodeBinding.inflate(inflater, container, false)

        binding.showmail.setText(viewModel.currentUser?.email ?: "not email provided")

        viewModel.resultVerifyCode.observe(viewLifecycleOwner){ result->
            showLoadAnimation(false)
            when(result) {
                Result.SUCCESS -> findNavController().navigate(R.id.action_new_password)
                Result.UNSUCCESS -> binding.layoutVerifycode.error = "El código ingresado es incorrecto"
                else -> {
                    val intent = Intent(context, ErrorActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
            }
        }

        binding.btnverifycode.setOnClickListener {
            val code = binding.verifycode.text.toString()
            if (code.isBlank() && code.length == 6) {
                binding.layoutVerifycode.error = "El código ingresado es incorrecto"
                return@setOnClickListener
            }
            showLoadAnimation(true)
            viewModel.entryVerificationCode(code)
        }

        binding.resendCode.setOnClickListener {
            viewModel.resendDataUser()
            Toast.makeText(requireContext(), "Reenviando codigo...", Toast.LENGTH_LONG).show()
        }

        binding.buttomClose.setOnClickListener { requireActivity().finish() }

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