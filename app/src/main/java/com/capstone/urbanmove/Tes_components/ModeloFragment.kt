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
    private var idMarca: Int? = null
    private var _binding: FragmentModeloBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ItemModeloAdapter
    private var listener: OnItemSelectedListener? = null

    companion object {
        private const val ARG_ID_MARCA = "id_marca"

        // Método para crear una instancia con el `idMarca` como argumento
        fun newInstance(idMarca: Int?): ModeloFragment {
            val fragment = ModeloFragment()
            val args = Bundle()
            args.putInt(ARG_ID_MARCA, idMarca ?: -1) // Pasa el id_marca o -1 si es null
            fragment.arguments = args
            return fragment
        }
    }

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

        // Recupera el idMarca de los argumentos
        idMarca = arguments?.getInt(ARG_ID_MARCA).takeIf { it != -1 }

        idMarca?.let { fetchModelos(it) }

        binding.ivClose.setOnClickListener {
            dismiss()
        }
    }

    private fun fetchModelos(idMarca: Int) {
        //Muestra progress bar hasta que se carguen los datos
        binding.progressBarModelo.visibility = View.VISIBLE
        binding.rvModelo.visibility = View.GONE
        lifecycleScope.launch {
            try {
                val modelos = RetrofitInstance.api.getModelos(idMarca)

                setupRecyclerView(modelos.sortedBy { it.modelo })
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
            // Ocultar al progress bar si se cargaron los datos
            binding.progressBarModelo.visibility = View.GONE
            binding.rvModelo.visibility = View.VISIBLE
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
            bottomSheet?.layoutParams?.height = 800 // Ajusta la altura en píxeles
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
