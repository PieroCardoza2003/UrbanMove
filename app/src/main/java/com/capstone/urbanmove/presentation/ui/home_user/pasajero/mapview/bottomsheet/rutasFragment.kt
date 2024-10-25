package com.capstone.urbanmove.presentation.ui.home_user.pasajero.mapview.bottomsheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.capstone.urbanmove.R
import com.capstone.urbanmove.databinding.FragmentRutasBinding
import com.capstone.urbanmove.databinding.FragmentTransportesBinding


class rutasFragment : Fragment() {

    private var _binding: FragmentRutasBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRutasBinding.inflate(inflater, container, false)

        binding.buttomClose.setOnClickListener {
            findNavController().navigate(R.id.action_to_transporte)
        }

        binding.buttonContinuar.setOnClickListener {
            findNavController().navigate(R.id.action_to_trayectoria)
        }

        return binding.root
    }


}