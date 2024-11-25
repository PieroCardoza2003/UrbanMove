package com.capstone.urbanmove.presentation.ui.home_user.conductor.registerDriver.empresa

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.capstone.urbanmove.databinding.FragmentRegisterDriverEmpresaBinding
import com.capstone.urbanmove.domain.entity.Result
import com.capstone.urbanmove.presentation.ui.common.ErrorActivity
import com.capstone.urbanmove.presentation.ui.common.LoadDialogFragment
import com.capstone.urbanmove.presentation.ui.home_user.conductor.DriverActivity
import com.capstone.urbanmove.presentation.ui.home_user.conductor.DriverViewModel

class RegisterDriverEmpresaFragment : Fragment() {

    private val viewModel: DriverViewModel by activityViewModels() //viewModel compartido
    private var _binding: FragmentRegisterDriverEmpresaBinding? = null
    private var loadDialogFragment: LoadDialogFragment? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterDriverEmpresaBinding.inflate(inflater, container, false)


        binding.btnAceptar.setOnClickListener {

            val codigo_empleado = binding.edittextCodigo.text.toString()

            if (codigo_empleado.isBlank()){
                binding.layoutCodigo.error = "Campo obligatorio"
                return@setOnClickListener
            }

            showLoadAnimation(true)
            viewModel.registrar_conductor_empresa(codigo_empleado)
        }

        viewModel.result_conductor_empresa.observe(viewLifecycleOwner){ result ->
            showLoadAnimation(false)
            when(result) {
                Result.SUCCESS -> {
                    Toast.makeText(requireContext(), "Registro exitoso", Toast.LENGTH_LONG).show()
                    val intent = Intent(context, DriverActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
                Result.UNSUCCESS -> {
                    Toast.makeText(requireContext(), "No se ha podido registrar, vuelva a intentarlo", Toast.LENGTH_LONG).show()
                    val intent = Intent(context, DriverActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
                else -> {
                    val intent = Intent(context, ErrorActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
            }
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