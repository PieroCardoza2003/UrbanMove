package com.capstone.urbanmove.presentation.ui.map.Funcion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.capstone.urbanmove.R
import com.capstone.urbanmove.presentation.ui.map.MapView

class fragment_2 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_2, container, false)

        // Configura el listener para button_continuar
        view.findViewById<Button>(R.id.button_continuar).setOnClickListener {
            (activity as? MapView)?.showFragment3() // Llama a showFragment3 en MapView
        }

        return view
    }
}
