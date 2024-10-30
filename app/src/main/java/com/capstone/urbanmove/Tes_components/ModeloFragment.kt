package com.capstone.urbanmove.Tes_components

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.urbanmove.databinding.FragmentModeloBinding
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class ModeloFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentModeloBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ItemModeloAdapter
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
        _binding = FragmentModeloBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchModelos()

        binding.ivClose.setOnClickListener {
            dismiss()
        }
    }

    private fun fetchModelos() {
        lifecycleScope.launch {
            try {
                val modelos = RetrofitInstance.api.getModelos()
                setupRecyclerView(modelos.sortedBy { it.id_modelo })
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRecyclerView(modelos: List<Modelo>) {
        adapter = ItemModeloAdapter(modelos) { item ->
            listener?.onItemSelected(item)
            dismiss()
        }

        binding.rvModelo.layoutManager = LinearLayoutManager(requireContext())
        binding.rvModelo.adapter = adapter
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

    interface OnItemSelectedListener {
        fun onItemSelected(item: Modelo)
    }
}
