package com.capstone.urbanmove.presentation.ui.home_user.pasajero.mapview.bottomsheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.capstone.urbanmove.R
import com.capstone.urbanmove.databinding.FragmentTrayectoriaBinding

class trayectoriaFragment : Fragment() {

    private var _binding: FragmentTrayectoriaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrayectoriaBinding.inflate(inflater, container, false)


        binding.buttomClose.setOnClickListener {
            findNavController().navigate(R.id.action_to_transporte)
        }

        binding.buttonIniciarBusqueda.setOnClickListener {
            findNavController().navigate(R.id.action_to_busqueda)
        }


        binding.btnIda.setOnClickListener{
            binding.btnIda.setBackgroundResource(R.drawable.bg_btn_border_selected)
            binding.btnVuelta.setBackgroundResource(R.drawable.bg_btn_border_unselected)
        }

        binding.btnVuelta.setOnClickListener{
            binding.btnVuelta.setBackgroundResource(R.drawable.bg_btn_border_selected)
            binding.btnIda.setBackgroundResource(R.drawable.bg_btn_border_unselected)
        }

        binding.buttonAlcance.setOnClickListener {
            findNavController().navigate(R.id.action_to_alcance)
        }

        return binding.root
    }

}