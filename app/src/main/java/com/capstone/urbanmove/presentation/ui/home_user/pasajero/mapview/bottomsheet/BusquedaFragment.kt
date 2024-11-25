package com.capstone.urbanmove.presentation.ui.home_user.pasajero.mapview.bottomsheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.capstone.urbanmove.R
import com.capstone.urbanmove.databinding.FragmentBusquedaBinding
import com.capstone.urbanmove.databinding.FragmentTransportesBinding
import com.capstone.urbanmove.presentation.ui.home_user.pasajero.PassengerViewModel


class BusquedaFragment : Fragment() {


    private var _binding: FragmentBusquedaBinding? = null
    private val binding get() = _binding!!
    private val viewmodelPassenger: PassengerViewModel by activityViewModels() //viewModel compartido

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBusquedaBinding.inflate(inflater, container, false)

        binding.buttomClose.setOnClickListener {
            viewmodelPassenger.cancelar_solicitud()
            findNavController().navigate(R.id.action_to_transporte)
        }

        return binding.root
    }

}