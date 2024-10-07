package com.capstone.urbanmove.presentation.ui.map.Funcion

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.capstone.urbanmove.R
import com.capstone.urbanmove.presentation.ui.map.MapView

class fragment_3 : Fragment() {

    private lateinit var toggleLinearLayout: LinearLayout
    private lateinit var toggleLinearLayout1: LinearLayout
    private val defaultBorderColor = Color.GRAY // Original border color

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_3, container, false)

        // Initialize views
        toggleLinearLayout = view.findViewById(R.id.toggleLinearLayout)
        toggleLinearLayout1 = view.findViewById(R.id.toggleLinearLayout1)

        // Set click listeners for the layouts
        toggleLinearLayout.setOnClickListener {
            toggleSelection(it, toggleLinearLayout1)
        }

        toggleLinearLayout1.setOnClickListener {
            toggleSelection(it, toggleLinearLayout)
        }
        view.findViewById<Button>(R.id.button_iniciar_busqueda).setOnClickListener {
            (activity as? MapView)?.showFragment4() // Llama a showFragment3 en MapView
        }

        return view
    }

    // Method to change the border color of a view
    private fun changeBorderColor(view: View, color: Int) {
        val newDrawable = GradientDrawable().apply {
            setStroke(4, color) // Adjust border width and color
            cornerRadius = 16f // Set corner radius if needed
            setColor(Color.TRANSPARENT) // Keep background transparent
        }
        view.background = newDrawable // Assign new drawable to the LinearLayout
    }

    // Method to toggle selection between two views
    private fun toggleSelection(selectedView: View, unselectedView: View) {
        selectedView.isSelected = true
        unselectedView.isSelected = false
        // Change border color depending on selection state
        changeBorderColor(selectedView, Color.RED)
        changeBorderColor(unselectedView, defaultBorderColor)
    }
}
