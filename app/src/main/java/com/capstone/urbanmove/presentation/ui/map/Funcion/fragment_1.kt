package com.capstone.urbanmove.presentation.ui.map.Funcion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.capstone.urbanmove.R
import com.capstone.urbanmove.presentation.ui.map.MapView

class fragment_1 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_1, container, false)

        view.findViewById<Button>(R.id.buttonColectivo).setOnClickListener {
            (activity as MapView).showFragment2() // Llama a la función en MapView
        }

        view.findViewById<Button>(R.id.buttonMicrobus).setOnClickListener {
            (activity as MapView).showFragment2() // Llama a la función en MapView
        }

        view.findViewById<Button>(R.id.buttonCombi).setOnClickListener {
            (activity as MapView).showFragment2() // Llama a la función en MapView
        }

        return view
    }
}
