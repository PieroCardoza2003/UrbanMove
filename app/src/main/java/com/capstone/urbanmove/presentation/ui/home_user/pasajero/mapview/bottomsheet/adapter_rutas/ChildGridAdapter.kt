package com.capstone.urbanmove.presentation.ui.home_user.pasajero.mapview.bottomsheet.adapter_rutas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.urbanmove.R
import com.capstone.urbanmove.presentation.ui.home_user.pasajero.mapview.bottomsheet.models.Ruta

class ChildGridAdapter(
    private val items: List<Ruta>,
    private val onItemClick: (Ruta) -> Unit // Callback para manejar el clic
) : RecyclerView.Adapter<ChildGridAdapter.ChildViewHolder>() {

    private var selectedPosition: Boolean = false

    class ChildViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val letra: TextView = view.findViewById(R.id.textview_letra_ruta)
        val empresa: TextView = view.findViewById(R.id.textview_empresa)
        val layout: LinearLayout = view.findViewById(R.id.layout_child_ruta)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_grid, parent, false)
        return ChildViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        val item = items[position]
        holder.letra.text = item.letra_ruta
        holder.empresa.text = item.empresa

        holder.itemView.setOnClickListener {
            if (!selectedPosition){
                holder.layout.setBackgroundResource(R.drawable.bg_btn_border_selected)
                onItemClick(item)
            } else {
                holder.layout.setBackgroundResource(R.drawable.bg_btn_border_unselected)
            }
            selectedPosition = !selectedPosition
        }
    }
    override fun getItemCount(): Int = items.size
}
