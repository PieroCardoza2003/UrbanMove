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
import com.capstone.urbanmove.presentation.ui.home_user.pasajero.mapview.bottomsheet.adapter_rutas.ExpandableListAdapter


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val titles = listOf("Microbús", "Colectivo", "Combi")
        val icons = mapOf(
            "Microbús" to R.drawable.icon_microbus,
            "Colectivo" to R.drawable.icon_colectivo,
            "Combi" to R.drawable.icon_combi
        )
        val data = mapOf(
            "Microbús" to listOf("A", "B", "C", "D"),
            "Colectivo" to listOf("AC", "BC", "BU", "AU", "CU", "AB"),
            "Combi" to listOf("E", "F", "G", "H")
        )

        val adapter = ExpandableListAdapter(requireContext(), titles, icons, data)
        binding.expandableListView.setAdapter(adapter)
    }


}