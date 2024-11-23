package com.capstone.urbanmove.presentation.ui.home_user.conductor.registerDriver.privado.vehicle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.urbanmove.databinding.FragmentMarcaVehiculoBinding
import com.capstone.urbanmove.presentation.ui.home_user.conductor.DriverViewModel
import com.capstone.urbanmove.presentation.ui.home_user.conductor.registerDriver.privado.vehicle.adapters.ItemMarcaAdapter
import com.capstone.urbanmove.presentation.ui.home_user.conductor.registerDriver.privado.vehicle.objects.VehicleMarca
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetMarca: BottomSheetDialogFragment() {

    private val viewModel: DriverViewModel by activityViewModels() //viewModel compartido
    private var _binding: FragmentMarcaVehiculoBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ItemMarcaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMarcaVehiculoBinding.inflate(inflater, container, false)

        viewModel.marcas_result.observe(viewLifecycleOwner){ marcas ->
            if (marcas.isNotEmpty()) {
                setupRecyclerView(marcas.sortedBy { it.marca })
            }

            binding.progressBarMarca.visibility = View.GONE
            binding.rvMarca.visibility = View.VISIBLE
        }

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.progressBarMarca.visibility = View.VISIBLE
        binding.rvMarca.visibility = View.GONE

        viewModel.fetchMarcas()

        binding.ivClose.setOnClickListener {
            dismiss()
        }
    }

    private fun setupRecyclerView(marcas: List<VehicleMarca>) {
        adapter = ItemMarcaAdapter(marcas) { item ->
            viewModel.v_marca.postValue(item)
            dismiss()
        }

        binding.rvMarca.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMarca.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        dialog?.let { dialog ->
            val bottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.layoutParams?.height = 800 // píxeles
            bottomSheet?.requestLayout()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}