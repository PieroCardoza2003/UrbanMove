package com.capstone.urbanmove.presentation.ui.home_user.conductor.registerDriver.privado.vehicle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.urbanmove.databinding.FragmentColorVehiculoBinding
import com.capstone.urbanmove.presentation.ui.home_user.conductor.DriverViewModel
import com.capstone.urbanmove.presentation.ui.home_user.conductor.registerDriver.privado.vehicle.adapters.ItemColorAdapter
import com.capstone.urbanmove.presentation.ui.home_user.conductor.registerDriver.privado.vehicle.objects.VehicleColor
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetColor: BottomSheetDialogFragment() {

    private val viewModel: DriverViewModel by activityViewModels() //viewModel compartido
    private var _binding: FragmentColorVehiculoBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ItemColorAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentColorVehiculoBinding.inflate(inflater, container, false)

        viewModel.colores_result.observe(viewLifecycleOwner){ colores ->
            if (colores.isNotEmpty()) {
                setupRecyclerView(colores.sortedBy { it.color })
            }

            binding.progressBarColor.visibility = View.GONE
            binding.rvColor.visibility = View.VISIBLE
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.progressBarColor.visibility = View.VISIBLE
        binding.rvColor.visibility = View.GONE

        viewModel.fetchColores()

        binding.ivClose.setOnClickListener {
            dismiss()
        }
    }

    private fun setupRecyclerView(colors: List<VehicleColor>) {
        adapter = ItemColorAdapter(colors) { item ->
            viewModel.v_color.postValue(item)
            dismiss()
        }

        binding.rvColor.layoutManager = LinearLayoutManager(requireContext())
        binding.rvColor.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        dialog?.let { dialog ->
            val bottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.layoutParams?.height = 800
            bottomSheet?.requestLayout()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}