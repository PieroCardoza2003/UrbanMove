package com.capstone.urbanmove.presentation.ui.map.Funcion.bottom

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstone.urbanmove.R
import com.capstone.urbanmove.presentation.ui.map.MapView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheet_Funcion : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "BottomSheet_Funcion"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla el layout del BottomSheet
        val view = inflater.inflate(R.layout.fragment_1, container, false)

        // Log para verificar la creación del fragmento
        Log.d(TAG, "Fragmento creado")

        // Configura los botones
        view.findViewById<View>(R.id.buttonColectivo).setOnClickListener {
            (activity as? MapView)?.showFragment2() // Llama a showFragment2 en MapView
            dismiss() // Cierra el BottomSheet
        }

        view.findViewById<View>(R.id.buttonMicrobus).setOnClickListener {
            (activity as? MapView)?.showFragment2() // Llama a showFragment2 en MapView
            dismiss() // Cierra el BottomSheet
        }

        view.findViewById<View>(R.id.buttonCombi).setOnClickListener {
            (activity as? MapView)?.showFragment2() // Llama a showFragment2 en MapView
            dismiss() // Cierra el BottomSheet
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        val bottomSheetDialog = dialog as BottomSheetDialog
        val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.let {
            val behavior = BottomSheetBehavior.from(it)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }
}
