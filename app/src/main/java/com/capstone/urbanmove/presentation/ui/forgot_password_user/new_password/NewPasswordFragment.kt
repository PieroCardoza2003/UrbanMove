package com.capstone.urbanmove.presentation.ui.forgot_password_user.new_password

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.capstone.urbanmove.databinding.FragmentNewPasswordBinding
import com.capstone.urbanmove.domain.entity.Result
import com.capstone.urbanmove.presentation.ui.common.ErrorActivity
import com.capstone.urbanmove.presentation.ui.common.LoadDialogFragment
import com.capstone.urbanmove.presentation.ui.forgot_password_user.ForgotPasswordViewModel


class NewPasswordFragment : Fragment() {

    private val viewModel: ForgotPasswordViewModel by activityViewModels() //viewModel compartido
    private var _binding: FragmentNewPasswordBinding? = null
    private val binding get() = _binding!!
    private var loadDialogFragment: LoadDialogFragment? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewPasswordBinding.inflate(inflater, container, false)

        viewModel.resultPassword.observe(viewLifecycleOwner){ result ->
            showLoadAnimation(false)
            when(result){
                Result.SUCCESS -> {
                    Toast.makeText(requireContext(), "Se cambio la contraseña correctamente", Toast.LENGTH_SHORT).show()
                    requireActivity().finish()
                }
                Result.UNSUCCESS -> {
                    Toast.makeText(requireContext(), "No se ha podido cambiar la contraseña", Toast.LENGTH_SHORT).show()
                    requireActivity().finish()
                }
                else -> {
                    val intent = Intent(context, ErrorActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
            }
        }

        binding.btnconfirmpassword.setOnClickListener {
            val newpassword = binding.edittextNewpassword.text.toString()
            val confirmpassword = binding.edittextConfirmpassword.text.toString()

            if (newpassword.isBlank()){
                binding.layoutNewpassword.errorIconDrawable = null
                binding.layoutNewpassword.error = "complete este campo"
                return@setOnClickListener
            }
            if (confirmpassword.isBlank()){
                binding.layoutConfirmpassword.errorIconDrawable = null
                binding.layoutConfirmpassword.error = "complete este campo"
                return@setOnClickListener
            }
            if (newpassword != confirmpassword){
                binding.layoutConfirmpassword.errorIconDrawable = null
                binding.layoutConfirmpassword.error = "La contraseña no coincide"
                return@setOnClickListener
            }
            showLoadAnimation(true)
            viewModel.entryPassword(newpassword)
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