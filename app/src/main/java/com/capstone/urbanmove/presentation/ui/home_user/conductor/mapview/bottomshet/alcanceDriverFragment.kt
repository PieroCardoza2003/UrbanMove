package com.capstone.urbanmove.presentation.ui.home_user.conductor.mapview.bottomshet

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
import com.capstone.urbanmove.databinding.FragmentAlcanceDriverBinding
import com.capstone.urbanmove.presentation.ui.home_user.conductor.mapview.MapViewModel


class alcanceDriverFragment : Fragment() {

    private var _binding: FragmentAlcanceDriverBinding? = null
    private val binding get() = _binding!!
    private val viewmodel: MapViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlcanceDriverBinding.inflate(inflater, container, false)

        binding.buttomClose.setOnClickListener {
            findNavController().navigate(R.id.action_to_ruta_driver)
        }

        binding.buttonGuardar.setOnClickListener {
            findNavController().navigate(R.id.action_to_ruta_driver)
        }

        binding.seekBarAlcance.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                val newRadius = 500 + (progress * 100)
                Log.d("prints", "enviando -> $newRadius")
                viewmodel.setRadius(newRadius)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

        return binding.root
    }

}