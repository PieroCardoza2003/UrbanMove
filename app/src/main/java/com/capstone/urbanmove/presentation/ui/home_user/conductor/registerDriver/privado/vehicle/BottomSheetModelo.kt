package com.capstone.urbanmove.presentation.ui.home_user.conductor.registerDriver.privado.vehicle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.urbanmove.databinding.FragmentModeloVehiculoBinding
import com.capstone.urbanmove.presentation.ui.home_user.conductor.DriverViewModel
import com.capstone.urbanmove.presentation.ui.home_user.conductor.registerDriver.privado.vehicle.adapters.ItemModeloAdapter
import com.capstone.urbanmove.presentation.ui.home_user.conductor.registerDriver.privado.vehicle.objects.VehicleModelo
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetModelo: BottomSheetDialogFragment() {

    private val viewModel: DriverViewModel by activityViewModels() //viewModel compartido
    private var _binding: FragmentModeloVehiculoBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ItemModeloAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentModeloVehiculoBinding.inflate(inflater, container, false)

        viewModel.modelos_result.observe(viewLifecycleOwner){ modelos ->
            if (modelos.isNotEmpty()) {
                setupRecyclerView(modelos.sortedBy { it.modelo })
            }

            binding.progressBarModelo.visibility = View.GONE
            binding.rvModelo.visibility = View.VISIBLE
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.progressBarModelo.visibility = View.VISIBLE
        binding.rvModelo.visibility = View.GONE

        viewModel.fetchModelos()

        binding.ivClose.setOnClickListener {
            dismiss()
        }
    }

    private fun setupRecyclerView(modelos: List<VehicleModelo>) {
        adapter = ItemModeloAdapter(modelos) { item ->
            viewModel.v_modelo.postValue(item)
            dismiss()
        }
        binding.rvModelo.layoutManager = LinearLayoutManager(requireContext())
        binding.rvModelo.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        dialog?.let { dialog ->
            val bottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.layoutParams?.height = 800 // Ajusta la altura en píxeles
            bottomSheet?.requestLayout()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}