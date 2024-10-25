package com.capstone.urbanmove.presentation.ui.home_user.pasajero.mapview.bottomsheet

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.capstone.urbanmove.R
import com.capstone.urbanmove.databinding.FragmentAlcanceBinding
import com.capstone.urbanmove.presentation.ui.home_user.pasajero.mapview.MapViewModel


class AlcanceFragment : Fragment() {

    private val viewModelMap: MapViewModel by activityViewModels() //viewModel compartido
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

        binding.seekBarAlcance.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                val newRadius = 500 + (progress * 100)
                Log.d("prints", "enviando -> $newRadius")
                viewModelMap.setRadius(newRadius)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}