package com.capstone.urbanmove.presentation.ui.home_user.pasajero.mapview.bottomsheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.capstone.urbanmove.R
import com.capstone.urbanmove.databinding.FragmentRutasBinding
import com.capstone.urbanmove.databinding.FragmentTransportesBinding
import com.capstone.urbanmove.presentation.ui.home_user.pasajero.PassengerViewModel
import com.capstone.urbanmove.presentation.ui.home_user.pasajero.mapview.bottomsheet.adapter_rutas.ExpandableListAdapter
import com.capstone.urbanmove.presentation.ui.home_user.pasajero.mapview.bottomsheet.models.Ruta


class rutasFragment : Fragment() {

    private var _binding: FragmentRutasBinding? = null
    private val binding get() = _binding!!
    private val viewModelPassenger: PassengerViewModel by activityViewModels() //viewModel compartido

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRutasBinding.inflate(inflater, container, false)

        viewModelPassenger.fetchRutas()

        binding.buttomClose.setOnClickListener {
            viewModelPassenger.cancelar_solicitud()
            findNavController().navigate(R.id.action_to_transporte)
        }

        binding.buttonContinuar.setOnClickListener {
            if (viewModelPassenger.rutas_selected.isEmpty()){
                Toast.makeText(requireContext(), "No ha seleccionado una ruta", Toast.LENGTH_SHORT).show()
            } else {
                findNavController().navigate(R.id.action_to_trayectoria)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelPassenger.listaRutas.observe(viewLifecycleOwner){ result ->
            if(result.isNotEmpty()) {
                // Agrupar rutas por tipo_transporte
                val groupedData = result.groupBy { it.tipo_transporte }

                // Generar títulos (grupos)
                val titles = listOf("Microbús", "Colectivo", "Combi")
                // Generar íconos según el tipo de transporte
                val icons = mapOf(
                    "Microbús" to R.drawable.icon_microbus,
                    "Colectivo" to R.drawable.icon_colectivo,
                    "Combi" to R.drawable.icon_combi
                )
                val adapter = ExpandableListAdapter(requireContext(), titles, icons, groupedData, viewModelPassenger)
                binding.expandableListView.setAdapter(adapter)
                binding.expandableListView.expandGroup(viewModelPassenger.selected_transporte)
            } else {
                Toast.makeText(requireContext(), "No hay rutas disponibles", Toast.LENGTH_SHORT).show()
            }
        }

    }


}