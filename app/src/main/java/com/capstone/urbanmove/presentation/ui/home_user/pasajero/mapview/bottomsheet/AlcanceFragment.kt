package com.capstone.urbanmove.presentation.ui.home_user.pasajero.mapview.bottomsheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.capstone.urbanmove.R
import com.capstone.urbanmove.databinding.FragmentAlcanceBinding


class AlcanceFragment : Fragment() {

    private var _binding: FragmentAlcanceBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlcanceBinding.inflate(inflater, container, false)


        binding.buttomClose.setOnClickListener {
            findNavController().navigate(R.id.action_to_transporte)
        }

        binding.buttonGuardar.setOnClickListener {
            findNavController().navigate(R.id.action_to_trayectoria)
        }

        return binding.root
    }


}