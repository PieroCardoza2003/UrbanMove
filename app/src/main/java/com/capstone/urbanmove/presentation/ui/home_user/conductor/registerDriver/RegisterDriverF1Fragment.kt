package com.capstone.urbanmove.presentation.ui.home_user.conductor.registerDriver

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.capstone.urbanmove.R
import com.capstone.urbanmove.databinding.FragmentRegisterDriverF1Binding
import com.capstone.urbanmove.presentation.ui.home_user.conductor.DriverViewModel
import com.capstone.urbanmove.presentation.ui.home_user.pasajero.PassengerActivity
import com.capstone.urbanmove.presentation.ui.login_user.LoginActivity


class RegisterDriverF1Fragment : Fragment() {

    private val viewModel: DriverViewModel by activityViewModels() //viewModel compartido
    private var _binding: FragmentRegisterDriverF1Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterDriverF1Binding.inflate(inflater, container, false)

        binding.btnMicrobusEmpresa.setOnClickListener{
            findNavController().navigate(R.id.action_to_informacion_personal)
            set_tipo_conductor("EMPRESA")
        }

        binding.btnCombiEmpresa.setOnClickListener{
            findNavController().navigate(R.id.action_to_informacion_personal)
            set_tipo_conductor("EMPRESA")
        }

        binding.btnColectivoPrivado.setOnClickListener{
            findNavController().navigate(R.id.action_to_informacion_personal)
            set_tipo_conductor("PRIVADO")
        }

        binding.buttonModoPasajero.setOnClickListener {
            val intent = Intent(requireContext(), PassengerActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        binding.buttonCerrarSesion.setOnClickListener {
            viewModel.closeSession()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        return binding.root
    }

    private fun set_tipo_conductor(tipo: String){
        viewModel.c_tipo_conductor = tipo
    }

}