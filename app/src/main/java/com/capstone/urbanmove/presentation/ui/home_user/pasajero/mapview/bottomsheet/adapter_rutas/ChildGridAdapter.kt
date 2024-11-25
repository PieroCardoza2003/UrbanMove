package com.capstone.urbanmove.presentation.ui.home_user.pasajero.mapview.bottomsheet.adapter_rutas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.urbanmove.R

class ChildGridAdapter(
    private val items: List<String>
) : RecyclerView.Adapter<ChildGridAdapter.ChildViewHolder>() {

    class ChildViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val letra: TextView = view.findViewById(R.id.textview_letra_ruta)
        val empresa: TextView = view.findViewById(R.id.textview_empresa)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_grid, parent, false)
        return ChildViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        holder.letra.text = items[position]
        //agregra empresa
    }
    override fun getItemCount(): Int = items.size
}
