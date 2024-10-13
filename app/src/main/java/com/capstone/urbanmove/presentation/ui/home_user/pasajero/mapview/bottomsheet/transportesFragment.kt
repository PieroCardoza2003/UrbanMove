package com.capstone.urbanmove.presentation.ui.home_user.pasajero.mapview.bottomsheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.capstone.urbanmove.R
import com.capstone.urbanmove.databinding.FragmentTransportesBinding


class transportesFragment : Fragment() {

    private var _binding: FragmentTransportesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransportesBinding.inflate(inflater, container, false)

        binding.btnTransporteMicrobus.setOnClickListener{
            Toast.makeText(requireContext(), "microbus", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_to_rutas)
        }

        binding.btnTransporteColectivo.setOnClickListener{
            Toast.makeText(requireContext(), "colectivo", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_to_rutas)
        }

        binding.btnTransporteCombi.setOnClickListener {
            Toast.makeText(requireContext(), "combi", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_to_rutas)
        }

        return binding.root
    }


}