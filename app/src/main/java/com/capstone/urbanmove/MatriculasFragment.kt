package com.capstone.urbanmove

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.urbanmove.databinding.FragmentMatriculasBinding
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MatriculasFragment: BottomSheetDialogFragment() {
    private var _binding: FragmentMatriculasBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ItemMatriculaAdapter
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
        _binding = FragmentMatriculasBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchMarcas()

        val ivClose = binding.ivClose
        ivClose.setOnClickListener {
            dismiss()
        }
    }
    private fun fetchMarcas() {
        lifecycleScope.launch {
            try {
                val marcas = RetrofitInstance.api.getMarcas()
                val marcaNombres = marcas.map { it.marca }.sorted()
                setupRecyclerView(marcaNombres)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRecyclerView(marcaNombres: List<String>) {
        adapter = ItemMatriculaAdapter(marcaNombres) { item ->
            listener?.onItemSelected(item) // Llama al método de la interfaz
            dismiss() // Cierra el BottomSheet después de la selección
        }

        binding.rvMatricula.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMatricula.adapter = adapter
    }

    override fun onStart() {
        super.onStart()

        // Ajusta el tamaño máximo para el BottomSheet
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
        fun onItemSelected(item: String)
    }
}