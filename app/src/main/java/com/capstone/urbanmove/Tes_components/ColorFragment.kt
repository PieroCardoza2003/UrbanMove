package com.capstone.urbanmove.Tes_components

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.urbanmove.databinding.FragmentColorBinding
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class ColorFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentColorBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ItemColorAdapter
    private var listener: OnItemSelectedListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnItemSelectedListener) {
            listener = context
        } else {
            throw ClassCastException("$context must implement OnItemSelectedListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentColorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchColors()

        binding.ivClose.setOnClickListener {
            dismiss()
        }
    }

    private fun fetchColors() {
        //Muestra progress bar hasta que se carguen los datos
        binding.progressBarColor.visibility = View.VISIBLE
        binding.rvColor.visibility = View.GONE
        lifecycleScope.launch {
            try {
                val colors = RetrofitInstance.api.getColor()
                setupRecyclerView(colors.sortedBy { it.color })
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }finally {
                // Ocultar al progress bar si se cargaron los datos
                binding.progressBarColor.visibility = View.GONE
                binding.rvColor.visibility = View.VISIBLE
            }
        }
    }

    private fun setupRecyclerView(colors: List<VehicleColor>) {
        adapter = ItemColorAdapter(colors) { item ->
            listener?.onItemSelected(item)
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

    interface OnItemSelectedListener {
        fun onItemSelected(item: VehicleColor)
    }
}
